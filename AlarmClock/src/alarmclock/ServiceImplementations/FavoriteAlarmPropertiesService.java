package alarmclock.ServiceImplementations;

import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import alarmclock.services.FavoriteAlarmService;
import alarmclock.services.PropertiesLoader;
import alarmclock.view.FavoriteAlarmPanel;
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
 * Properties Loader service to save and load properties.  It will save our
 * favorite alarms as a set of Properties into the Properties Loader.
 * 
 * This class Does Something.
 * @author Gordon
 */
public class FavoriteAlarmPropertiesService implements FavoriteAlarmService{

    /*
     * This is an example of Dependency Injection.  This service depends on
     * another service, that is a PropertiesLoader.  The Properties loader
     * is the service that performs the loading and saving of properties to
     * wherever.  The FavoriteAlarmPropertiesService doesn't care where the
     * properties are saved, it only cares that it can load and save them.
     * 
     * Thus for separation of concerns, the PropertiesLoader service is injected
     * by the program before this class is used.
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
        Properties props = this.loader.loadProperties("FavoriteAlarms.properties");
        
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
        FavoriteAlarm ret = new FavoriteAlarm(timeOfDay, path);
        
        Set<FavoriteAlarm> favorites = this.getFavorites();
        favorites.add(ret);
        this.saveFavorites(favorites);
        
        return ret;
    }

    @Override
    public boolean DeleteFavorite(FavoriteAlarm favorite) {
        Set<FavoriteAlarm> favorites = this.getFavorites();
        
        boolean wasRemoved = favorites.remove(favorite);
        
        this.saveFavorites(favorites);
        
        return wasRemoved;
    }

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
            logger.log(Level.WARNING, "Could not parse string " + str + ": " + ex.toString());
            
            return null;
        }
        
    }
    
    
    
}
