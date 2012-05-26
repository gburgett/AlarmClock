package alarmclock.ServiceImplementations;

import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import alarmclock.services.FavoriteAlarmService;
import alarmclock.services.PropertiesLoader;
import alarmclock.view.FavoriteAlarmPanel;
import java.io.IOException;
import java.util.Comparator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This service implementation implements the FavoriteAlarmService using an injected
 * Properties Loader service to store persistent data.  It will save our
 * favorite alarms as a set of Properties into the Properties Loader, which will
 * save them to persistent storage somehow.  This class doesn't care how.
 * 
 * This class Does Something.
 * @author Gordon
 */
public class FavoriteAlarmPropertiesService implements FavoriteAlarmService{

    /*
     * The string "FavoriteAlarms.properties", which is the file name
     * where we will be saving our favorite alarms, is what is known as a
     * "Magic String".  Instead of putting the Magic String as a literal
     * everywhere it is used, we declare it once here and use the constant
     * variable "FileName" to refer to it.  This way we only have to modify
     * the magic string in one place if we need to change it.
     */
    static final String FileName = "FavoriteAlarms.properties";
    
    /*
     * This is an example of Dependency Injection.  This service depends on
     * another service, that is a PropertiesLoader.  The Properties loader
     * is the service that performs the loading and saving of properties to
     * wherever.  The FavoriteAlarmPropertiesService doesn't care where the
     * properties are saved, it only cares that it can load and save them.
     * 
     * This becomes very useful for Unit Tests, when we want to test the
     * functionality of this class without having to write and load an actual
     * file using an actual properties loader.  We can mock out the properties
     * loader interface and give a fake properties loader to this class, then
     * we can tightly control the test conditions.
     * See an example of this in FavoriteAlarmPropertiesServiceTest.java
     * 
     * Thus for separation of concerns, the PropertiesLoader service is injected
     * by the program before this class is used.  We only need a setter because
     * it is injected once and used internally.
     */
    private PropertiesLoader loader;
    public void setPropertiesLoader(PropertiesLoader loader){
        this.loader = loader;
    }
    
    /**
     * This implements the getFavorites() method from the interface.  One cool
     * thing about java is that implementations of an interface method can return
     * a more narrow return type than is required by the interface and it's all cool.
     * The interface requires an Iterable of FavoriteAlarm, but this method
     * returns a Set of FavoriteAlarm.  That's all cool because a Set Is an Iterable.
     * The set is more narrow, therefore anything that expects an Iterable but gets
     * a set will be OK.
     * @return 
     */
    @Override
    public Set<FavoriteAlarm> getFavorites(){
        Properties props = this.loader.loadProperties(FileName);
        
        Set<FavoriteAlarm> favorites = new java.util.TreeSet<FavoriteAlarm>(
            new Comparator<FavoriteAlarm>(){
                @Override
                public int compare(FavoriteAlarm o1, FavoriteAlarm o2) {
                    return o1.getTimeOfDay().compareTo(o2.getTimeOfDay());
                }
            });

        for(String s : props.stringPropertyNames()){
            FavoriteAlarm alm = this.Deserialize(s);
            if(alm != null){
                favorites.add(alm);
            }
        }
        return favorites;
    } 
    
    /**
     * This is a private utility method that saves the favorites to the injected
     * properties loader.
     * @param favorites The set of favorites to save.  It is an iterable because
     * we don't need it to be any more narrow.  It is good practice to accept
     * as wide of a data type as possible, only narrowing as much as necessary.
     */
    private void saveFavorites(Iterable<FavoriteAlarm> favorites){
        Properties props = new Properties();
        
        //All we do with the favorites is iterate over them, so we only care that
        //the favorites are iterable.  They can be any iterable thing, such as
        //a set or list or even an array.
        for(FavoriteAlarm alm : favorites){
            String key = this.Serialize(alm);
            props.setProperty(key, "");
        }
        
        try {
            //Take a look at the file after you save a couple favorites.  You can
            //open it in notepad and see what it looks like.
            this.loader.saveProperties(FileName, props);
        } catch (IOException ex) {
            Logger.getLogger(FavoriteAlarmPropertiesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Saves and returns a new FavoriteAlarm for the given time of day and
     * executable path.  This implements the interface method.
     * @param timeOfDay the timeOfDay of the favorite alarm
     * @param path the executable path of the favorite alarm.
     * @return the newly created FavoriteAlarm object.
     */
    @Override
    public FavoriteAlarm SaveFavorite(LocalTime timeOfDay, String path) {
        //round it to the nearest minute because that's the best resolution
        //we can serialize to
        timeOfDay = timeOfDay.minuteOfHour().roundFloorCopy();
        
        FavoriteAlarm ret = new FavoriteAlarm(timeOfDay, path);
        
        Set<FavoriteAlarm> favorites = this.getFavorites();
        favorites.add(ret);
        this.saveFavorites(favorites);
        
        return ret;
    }

    /**
     * Deletes a favorite alarm out of the persistent data store.
     * @param favorite The FavoriteAlarm to delete
     * @return true if the FavoriteAlarm was able to be deleted, false otherwise
     */
    @Override
    public boolean DeleteFavorite(FavoriteAlarm favorite) {
        Set<FavoriteAlarm> favorites = this.getFavorites();
        
        boolean wasRemoved = favorites.remove(favorite);
        
        this.saveFavorites(favorites);
        
        return wasRemoved;
    }

    /**
     * Converts a FavoriteAlarm into a SetAlarm, whose execution time is the
     * same time of day as the favorite within the next 24 hours.
     * @param favorite the FavoriteAlarm to convert
     * @return the resulting converted SetAlarm
     */
    @Override
    public SetAlarm CreateAlarm(FavoriteAlarm favorite) {
        
        DateTime alarmTime = favorite.getTimeOfDay().toDateTimeToday();
        
        if(alarmTime.isBeforeNow()){
            alarmTime = alarmTime.plusDays(1);
        }
        
        SetAlarm ret = new SetAlarm(alarmTime, favorite.getPath());
        return ret;
    }
    
    /*
     * Below is an instance of Serialization and Deserialization.  In this case
     * we are serializing FavoriteAlarm objects into and out of Strings.  We are
     * using the character "|" as a delimiter, that is, a character that does not
     * normally appear in the normal string representation of the properties of
     * the FavoriteAlarm which we can use to separate the property values in
     * the string.
     */
    
    private static DateTimeFormatter serializeFormatter = DateTimeFormat.forPattern("h:mm a");
    public String Serialize(FavoriteAlarm alarm){
        //Here we are combining the values of the FavoriteAlarm into a string.
        //Example: "10:00 AM|www.pandora.com"
        String ret = alarm.getTimeOfDay().toString(serializeFormatter) + "|" + alarm.getPath();
        return ret;
    }
    
    public FavoriteAlarm Deserialize(String str)
    {
        //Here we are using the delimiter to separate the values contained in the string
        String[] strs = str.split("\\|");
        if(strs.length < 2) return null;
        
        
        try {
            //Parse out the DateTime value from the first part of the string.
            DateTime time = serializeFormatter.parseDateTime(strs[0]);
            if (time == null) {
                return null;
            }
            
            //Pull the exePath out of the second part of the string.
            String exePath = strs[1];

            LocalTime localTime = time.toLocalTime();
                        
            return new FavoriteAlarm(localTime, exePath);
            
        } catch (Exception ex) {
            //if there is an error, log it and return a null value indicating we could
            //not deserialize the string.
            Logger logger = Logger.getLogger(FavoriteAlarmPanel.class.getName());
            logger.log(Level.WARNING, "Could not parse string {0}: {1}", new Object[]{str, ex.toString()});
            
            return null;
        }
        
    }
    
    
    
}
