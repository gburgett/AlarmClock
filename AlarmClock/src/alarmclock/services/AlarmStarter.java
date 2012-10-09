package alarmclock.services;

import alarmclock.models.SetAlarm;
import java.util.TimerTask;
import org.joda.time.DateTime;

/**
 * This interface abstracts away the tasks of creating and starting alarms using
 * a timer.  This is done to separate the service code from the UI code.
 *
 * This interface defines the contract for classes which Do Something.
 * @author Gordon
 */
public interface AlarmStarter {
    /**
     * Creates a new Alarm object for the given date and executable file.
     * @param alarmDate The date in the future for which the alarm should be scheduled.
     * @param exePath The path to the file or website to start.
     * @return the new Alarm object
     */
    public SetAlarm createAlarm(DateTime alarmDate, String exePath);

    /**
     * Starts an Alarm, and runs the given task when the alarm goes off.
     * @param alarm The alarm object to run, created using the createAlarm method
     * of this class.
     * @param whenFinished The executable to run when finished.
     * @return the TimerTask which can be used to monitor or cancel the running task.
     * @throws Exception
     */
    public TimerTask startAlarm(SetAlarm alarm, Runnable whenFinished)
            throws Exception;

    /**
     * Cancels an Alarm, preventing it from going off in the future.  After
     * being canceled, the AlarmStarter will forget about the alarm.
     * @param alarm The alarm to cancel
     * @return True if the alarm was successfully canceled, false otherwise.
     */
    public boolean cancelAlarm(SetAlarm alarm);

}
