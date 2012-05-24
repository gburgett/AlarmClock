/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.ServiceImplementations;

import alarmclock.models.SetAlarm;
import java.util.TimerTask;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
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
        DateTime alarmDate = null;
        String exePath = "";
        TimerAlarmStarter instance = new TimerAlarmStarter();
        SetAlarm expResult = null;
        SetAlarm result = instance.CreateAlarm(alarmDate, exePath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of StartAlarm method, of class TimerAlarmStarter.
     */
    @org.junit.Test
    public void testStartAlarm() throws Exception {
        System.out.println("StartAlarm");
        SetAlarm alarm = null;
        Runnable whenFinished = null;
        TimerAlarmStarter instance = new TimerAlarmStarter();
        TimerTask expResult = null;
        TimerTask result = instance.StartAlarm(alarm, whenFinished);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CancelAlarm method, of class TimerAlarmStarter.
     */
    @org.junit.Test
    public void testCancelAlarm() {
        System.out.println("CancelAlarm");
        SetAlarm alarm = null;
        TimerAlarmStarter instance = new TimerAlarmStarter();
        boolean expResult = false;
        boolean result = instance.CancelAlarm(alarm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
