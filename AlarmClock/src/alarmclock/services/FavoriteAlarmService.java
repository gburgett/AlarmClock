/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.services;

import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import org.joda.time.LocalTime;

/**
 * This interface sets the contract for a service which creates, saves and loads
 * FavoriteAlarms.  It will use some kind of persistent storage to save and load
 * alarms between opening and closing the program.
 * 
 * This interface defines the contract for classes which Do Something.
 * @author Gordon
 */
public interface FavoriteAlarmService {
    
    /**
     * Loads all the known FavoriteAlarms from persistent storage and
     * returns them as an {@link Iterable}.
     * @return All the known FavoriteAlarms.
     */
    public Iterable<FavoriteAlarm> getFavorites();
    
    /**
     * Creates a new FavoriteAlarm object and saves it to persistent storage.
     * @param timeOfDay The Time of Day for which the alarm should be set when
     * the user requests it.
     * @param path The Path to the file that should be executed when the timer goes off.
     * @return The new FavoriteAlarm object.
     */
    public FavoriteAlarm SaveFavorite(LocalTime timeOfDay, String path);

    /**
     * Deletes a FavoriteAlarm from persistent storage.
     * @param favorite The Favorite to delete.
     * @return True if the delete was successful, false otherwise.
     */
    public boolean DeleteFavorite(FavoriteAlarm favorite);
    
    /**
     * Turns a FavoriteAlarm into a SetAlarm which will go off in the next 24
     * hours at the FavoriteAlarm's time of day.
     * @param favorite the FavoriteAlarm to convert
     * @return the newly created SetAlarm which can be started with a {@link AlarmStarter}
     * service.
     */
    public SetAlarm CreateAlarm(FavoriteAlarm favorite);
}
