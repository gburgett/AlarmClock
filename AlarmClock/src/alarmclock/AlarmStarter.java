/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import org.joda.time.DateTime;

/**
 *
 * @author Gordon
 */
public interface AlarmStarter {
    public SetAlarm startAlarm(DateTime alarmDate, String exePath);
}
