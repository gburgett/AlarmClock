/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock;

import java.util.Date;
import java.util.EventListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.joda.time.DateTime;

/**
 *
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
    
    private TimerTask task;
    
    private Timer timer;
    public void setTimer(Timer timer){
        this.timer = timer;
    }
    
    private ProcessStarter pStarter;
    public void setProcessStarter(ProcessStarter pStarter){
        this.pStarter = pStarter;
    }
    
    public SetAlarm(DateTime time, String path){
        this.time = time;
        this.path = path;
    }
    
    public void Start()
            throws Exception
    {
        if(this.task != null)
            throw new Exception("Already scheduled once");
                            
        long millis = this.time.getMillis() - System.currentTimeMillis();
        if(millis < 0 || millis > SetAlarm.MAX_FUTURE_SCHEDULING_MILLIS)
            throw new Exception("can't schedule for " + millis + "from now");
        
        task = new TimerTask(){
            @Override
            public void run() {                   
                try{
                    SetAlarm.this.pStarter.runFile(path);
                }catch(Exception ex){
                    SetAlarm.this.fireError(ex);
                }
                SetAlarm.this.fireFinished(null);
            }            
        };
        timer.schedule(task, millis);
    }
    
    public void cancel(){
        if(this.task != null){
            this.task.cancel();
            this.fireCancel(null);
        }
    }
    
    
    
    
    private List<AlarmListener> cancelListeners = new java.util.ArrayList();
    public void addAlarmListener(AlarmListener l){
        this.cancelListeners.add(l);
    }
    public void removeAlarmListener(AlarmListener l){
        this.cancelListeners.remove(l);
    }
    private void fireCancel(java.util.EventObject evt){
        AlarmListener[] arr = new AlarmListener[0];
        arr = this.cancelListeners.toArray(arr);
        for(AlarmListener l : arr){
            l.cancelled(evt);
        }
    }
    private void fireFinished(java.util.EventObject evt){
        AlarmListener[] arr = new AlarmListener[0];
        arr = this.cancelListeners.toArray(arr);
        for(AlarmListener l : arr){
            l.finished(evt);
        }
    }
    private void fireError(Throwable ex){
        AlarmListener[] arr = new AlarmListener[0];
        arr = this.cancelListeners.toArray(arr);
        for(AlarmListener l : arr){
            l.error(ex);
        }
    }
    public interface AlarmListener extends EventListener{
        public void cancelled(java.util.EventObject evt);
        
        public void finished(java.util.EventObject evt);
        
        public void error(Throwable ex);
    }
}
