package alarmclock.ServiceImplementations;

import alarmclock.models.SetAlarm;
import alarmclock.services.AlarmStarter;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.joda.time.DateTime;

/**
 * This class is a Service which implements the AlarmStarter interface.
 *
 * This class Does Something.
 * @author Gordon
 */
public class TimerAlarmStarter implements AlarmStarter {

    /** This is the timer that actually runs the alarms */
    private Timer timer = new Timer();

    /**
     * This is a collection that simply remembers our currently running timers.
     * It has to be synchronized because it will be modified concurrently by multiple
     * threads, specifically the UI thread and also the timer thread.
     */
    private Map<SetAlarm, TimerTask> runningTasks =
            Collections.synchronizedMap(new java.util.HashMap());

    /**
     * This method implements the createAlarm function for the TimerAlarmStarter.
     * It creates and returns a new Alarm for the given time and path
     * @param alarmDate the Time when the alarm should go off
     * @param exePath the Path of the executable or file that should be started
     * @return a new Alarm object containing that data.
     */
    @Override
    public SetAlarm createAlarm(DateTime alarmDate, String exePath)
    {
        return new SetAlarm(alarmDate, exePath);
    }

    /**
     * Starts the given SetAlarm using a timer.
     * @param alarm the alarm to start
     * @param whenFinished A callback task to be executed when the alarm starts
     * @return a new TimerTask which can be used to monitor and cancel the alarm timer
     * @throws Exception if the alarm is not valid or has already been started.
     */
    @Override
    public TimerTask startAlarm(final SetAlarm alarm, final Runnable whenFinished)
            throws Exception
    {
        //this section is validation on the given alarm
        if (runningTasks.containsKey(alarm)){
            throw new UnsupportedOperationException("Already scheduled once");
        }

        //get the number of milliseconds in the future we want to set the alarm for.
        long millis = alarm.getTime().getMillis() - System.currentTimeMillis();
        if(millis < 0 || millis > SetAlarm.MAX_FUTURE_SCHEDULING_MILLIS)
            throw new UnsupportedOperationException("can't schedule for " + millis + "from now");

        //here we are creating the new TimerTask that will be executed by the timer.
        TimerTask ret = new TimerTask(){
            @Override
            public void run() {
                //run the given task when the timer goes off
                whenFinished.run();

                //and remove it from our remembered tasks
                runningTasks.remove(alarm);
            }

            /**
             * We are overriding cancel here so we can make sure it gets
             * removed from our runningTasks list.
             */
            @Override
            public boolean cancel(){
                //call into the base class' cancel() implementation
                boolean ret = super.cancel();

                runningTasks.remove(alarm);

                return ret;
            }
        };

        //Remember the task for later in case we need to cancel
        runningTasks.put(alarm, ret);

        //We are now scheduling the task in the timer.  The timer runs in a separate
        //thread and will execute the run() method of the timer task when the
        //given date in millis is reached.
        timer.schedule(ret, millis);

        return ret;
    }


    /**
     * Cancels a given alarm that has already been started
     * @param alarm the currently running alarm
     * @return true if the alarm could be canceled, false otherwise.
     */
    @Override
    public boolean cancelAlarm(SetAlarm alarm){
        //We pull the TimerTask object out of our map of tasks that we remember.
        TimerTask task = runningTasks.get(alarm);
        if (task == null)
            return false;

        //try to cancel it
        boolean cancelled = task.cancel();

        if(cancelled)
            runningTasks.remove(alarm);

        return cancelled;
    }

}
