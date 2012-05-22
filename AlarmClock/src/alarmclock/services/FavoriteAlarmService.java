/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.services;

import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import org.joda.time.LocalTime;

/**
 *
 * @author Gordon
 */
public interface FavoriteAlarmService {
    
    public Iterable<FavoriteAlarm> getFavorites();
    
    public FavoriteAlarm SaveFavorite(LocalTime timeOfDay, String path);

    public boolean DeleteFavorite(FavoriteAlarm favorite);
    
    public SetAlarm CreateAlarm(FavoriteAlarm favorite);
}
