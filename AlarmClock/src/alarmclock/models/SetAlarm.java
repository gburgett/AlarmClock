package alarmclock.models;

import alarmclock.services.AlarmStarter;
import alarmclock.services.ProcessStarter;
import java.util.EventListener;
import java.util.List;
import java.util.TimerTask;
import org.joda.time.DateTime;

/**
 * This class represents one currently set alarm.  It contains the date and time
 * when the alarm will go off, as well as the path of the program or file
 * to be executed when the alarm goes off.  It will function as the data model
 * for the UI.
 * 
 * This class Is Something.
 * @author Gordon
 */
public class SetAlarm {
    /** Let's say 1 month */
    public static final long MAX_FUTURE_SCHEDULING_MILLIS = 31l * 24l * 60l * 60l * 1000l;
    
    private DateTime time;
    public DateTime getTime(){
        return time;
    }
    
    private String path;
    public String getPath(){
        return path;
    }
    
    public SetAlarm(DateTime time, String path){
        this.time = time;
        this.path = path;
    }

    /*
     * Since this class Is Something, that is it represents some data 
     * (namely the exact Time and executable path of a currently set alarm),
     * it should override the equals and getHashCode methods.  
     * Normally an object is only equal to another object if they are the exact
     * same instance.  Here we are overriding that to say that two Favorites are
     * equal if they have the same time and the same path, even if they
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
        final SetAlarm other = (SetAlarm) obj;
        if (this.time != other.time && (this.time == null || !this.time.equals(other.time))) {
            return false;
        }
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.time != null ? this.time.hashCode() : 0);
        hash = 83 * hash + (this.path != null ? this.path.hashCode() : 0);
        return hash;
    }
    
    
}
