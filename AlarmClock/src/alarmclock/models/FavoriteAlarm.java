package alarmclock.models;

import org.joda.time.LocalTime;

/**
 * This class represents one FavoriteAlarm saved in the Favorites section.
 *
 * This class Is Something.
 * @author Gordon
 */
public class FavoriteAlarm {

    /**
     * This is a read-only property called TimeOfDay.
     * A property consists of up to 3 parts: <br/>
     * 1) a backing field <br/>
     * 2) an optional getter method <br/>
     * 3) an optional setter method <br/>
     * <p/>
     * Here we have included only the getter method because this property
     * should not be set after the creation of the FavoriteAlarm.
     * <p/>
     * Properties are very useful.  They are used to control access to the fields
     * of a class.  Suppose after you have been developing for a while that you
     * wanted to restrict the timeOfDay so that it could only be before noon.
     * If you had been using the field directly, you would have to refactor every
     * place you used the field.  Since we use a property, we can simply modify
     * the setter method to throw an exception if the timeOfDay is set to
     * an invalid value.
     */
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
