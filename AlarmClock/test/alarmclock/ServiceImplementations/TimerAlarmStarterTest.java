/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.ServiceImplementations;

import alarmclock.models.SetAlarm;
import alarmclock.testutils.Box;
import java.util.TimerTask;
import static org.hamcrest.core.Is.is;
import org.joda.time.DateTime;
import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the TimerAlarmStarter service implementation.
 * Since this class Does Something and is fairly complex, we need unit tests for it.
 * We make sure the unit tests are only testing this object by mocking or stubbing
 * any classes that this service relies upon.  This is why we use dependency injection.
 * @author Gordon
 */
public class TimerAlarmStarterTest {
    
    public TimerAlarmStarterTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of CreateAlarm method, of class TimerAlarmStarter.
     */
    @org.junit.Test
    public void testCreateAlarm() {
        System.out.println("CreateAlarm");
        //setup
        DateTime alarmDate = new DateTime();
        String exePath = "www.test.com";
        
        TimerAlarmStarter instance = new TimerAlarmStarter();
        
        //act
        SetAlarm result = instance.CreateAlarm(alarmDate, exePath);
        
        //assert
        assertEquals(new SetAlarm(alarmDate, exePath), result);        
    }

    /**
     * Test of StartAlarm method, of class TimerAlarmStarter.
     */
    @org.junit.Test
    public void testStartAlarm() throws Exception {
        System.out.println("StartAlarm");
        
        //setup
        SetAlarm alarm = new SetAlarm(new DateTime().plusMillis(100), "www.test.com");
        //we're using a box to hold the value because the box has to be final
        //so that we can reference it in the runnable
        final Box<Boolean> wasRun = new Box<Boolean>(false);
        
        Runnable whenFinished = new Runnable(){
            @Override
            public void run() {
                //set the value in the box
                wasRun.setValue(true);
            }            
        };
        
        TimerAlarmStarter instance = new TimerAlarmStarter();
        
        //act
        TimerTask result = instance.StartAlarm(alarm, whenFinished);
        
        //assert
        assertNotNull(result);
            //verify equality to within 10 milliseconds
        assertEquals(alarm.getTime().getMillis(), result.scheduledExecutionTime(), 10);
        assertFalse("value shouldn't get set till after timer goes off",
                wasRun.isValueSet());
        
        //wait out the alarm
        Thread.sleep(100);
        
        wasRun.VerifySet();
        assertTrue(wasRun.getValue());
    }

    @org.junit.Test
    public void testStartAlarm_InvalidTime_ThrowsException() throws Exception {
        System.out.println("testStartAlarm_InvalidTime_ThrowsException");

        //setup
        //set the alarm for in the past
        SetAlarm alarm = new SetAlarm(new DateTime().minusMillis(100), "www.test.com");

        final Box<Boolean> wasRun = new Box<Boolean>(false);
        
        Runnable whenFinished = new Runnable(){
            @Override
            public void run() {
                //set the value in the box
                wasRun.setValue(true);
            }            
        };
        
        TimerAlarmStarter instance = new TimerAlarmStarter();
        
        try
        {
            //act
            TimerTask result = instance.StartAlarm(alarm, whenFinished);
            
            //assert
            //this is how you test that there should be an exception
            fail("Should have thrown an exception");
        }        
        catch(Exception e)
        {
            //we can perform assertions on the exception that was thrown
            //here we are using a Hamcrest "is" matcher to assert that
            //the object is an instance of UnsupportedOperationException
            assertThat(e, is(UnsupportedOperationException.class));
        }
    }
    
    /**
     * Test of CancelAlarm method, of class TimerAlarmStarter.
     */
    @org.junit.Test
    public void testCancelAlarm() throws Exception {
        System.out.println("CancelAlarm");
        
        SetAlarm alarm = new SetAlarm(new DateTime().plusMillis(100), "www.test.com");
        final Box<Boolean> wasRun = new Box<Boolean>(false);
        
        Runnable whenFinished = new Runnable(){
            @Override
            public void run() {
                wasRun.setValue(true);
            }            
        };
        
        TimerAlarmStarter instance = new TimerAlarmStarter();
        TimerTask result = instance.StartAlarm(alarm, whenFinished);
        
        //act
        instance.CancelAlarm(alarm);
        
        //wait out the alarm
        Thread.sleep(100);
        
        //assert        
        assertFalse("value should never have been set because the alarm was cancelled",
                wasRun.isValueSet());
    }
    
    @org.junit.Test
    public void testCancelByTimerTask() throws Exception {
        System.out.println("testCancelByTimerTask");

        //setup
        SetAlarm alarm = new SetAlarm(new DateTime().plusMillis(100), "www.test.com");
        final Box<Boolean> wasRun = new Box<Boolean>(false);
        
        Runnable whenFinished = new Runnable(){
            @Override
            public void run() {
                wasRun.setValue(true);
            }            
        };
        
        TimerAlarmStarter instance = new TimerAlarmStarter();
        TimerTask result = instance.StartAlarm(alarm, whenFinished);
        
        //act
        result.cancel();        
        
        //wait out the alarm
        Thread.sleep(100);
        
        //assert        
        assertFalse("value should never have been set because the alarm was cancelled",
                wasRun.isValueSet());
    }
}
