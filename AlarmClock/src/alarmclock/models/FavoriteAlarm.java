package alarmclock.models;

import org.joda.time.LocalTime;

/**
 * This class represents one FavoriteAlarm saved in the Favorites section.
 * 
 * This class Is Something.
 * @author Gordon
 */
public class FavoriteAlarm {
    
    private LocalTime timeOfDay;
    public LocalTime getTimeOfDay(){
        return timeOfDay;
    }
    
    private String path;
    public String getPath(){
        return path;
    }
    
    public FavoriteAlarm(LocalTime timeOfDay, String path){
        this.timeOfDay = timeOfDay;
        this.path = path;
    }

    /*
     * Since this class Is Something, that is it represents some data 
     * (namely the Time of Day and path of a favorite alarm), it should override
     * the equals and getHashCode methods.  Normally an object is only equal
     * to another object if they are the exact same instance.  Here we are
     * overriding that to say that two Favorites are equal if they have the
     * same timeOfDay and the same path, even if they
     * are two different instances.
     * 
     * Whenever you override the equals method you MUST also override the
     * getHashCode method.  Netbeans will generate this code automatically
     * for you if you hit alt-insert.
     */
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FavoriteAlarm other = (FavoriteAlarm) obj;
        if (this.timeOfDay != other.timeOfDay && (this.timeOfDay == null || !this.timeOfDay.equals(other.timeOfDay))) {
            return false;
        }
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.timeOfDay != null ? this.timeOfDay.hashCode() : 0);
        hash = 43 * hash + (this.path != null ? this.path.hashCode() : 0);
        return hash;
    }
    
    
}
