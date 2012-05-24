/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.ServiceImplementations;

import alarmclock.ServiceStubs.*;
import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import static org.junit.Assert.*;

/**
 *
 * @author Gordon
 */
public class FavoriteAlarmPropertiesServiceTest {
    
    public FavoriteAlarmPropertiesServiceTest() {
    }

    /**
     * Test of getFavorites method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testGetFavorites_NoFavorites_EmptyList() {
        System.out.println("getFavorites, empty properties, empty list");
        
        //First thing: setup your test variables
        final Properties testProperties = new Properties();
        
        //next create the instance under test
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        //then override the stubs and/or setup the initial conditions
        instance.setPropertiesLoader(new PropertiesLoaderStub(){
            @Override
            public Properties loadProperties(String filename) {
                return testProperties;
            }
        });
        
        //now Act
        Set<FavoriteAlarm> actual = instance.getFavorites();
        
        //finally, Assert
        assertEquals("There should be no favorites", 0, actual.size());        
    }
    
    @org.junit.Test
    public void testGetFavorites_HasFavorites_ContainedInList() {
        System.out.println("testGetFavorites_HasFavorites_ContainedInList");

        //setup
        final Properties testProperties = new Properties();
        
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        instance.setPropertiesLoader(new PropertiesLoaderStub(){
            @Override
            public Properties loadProperties(String filename) {
                return testProperties;
            }
        });
        testProperties.setProperty("8:00 AM|www.pandora.com", "");
        testProperties.setProperty("12:00 PM|www.testurl.com", "");
        
        //act
        Set<FavoriteAlarm> actual = instance.getFavorites();

        //assert
        assertEquals(2, actual.size());
        FavoriteAlarm[] arr = actual.toArray(new FavoriteAlarm[2]);
        assertEquals(new LocalTime(8, 00), arr[0].getTimeOfDay());
        assertEquals("www.pandora.com", arr[0].getPath());
        assertEquals(new LocalTime(12, 00), arr[1].getTimeOfDay());
        assertEquals("www.testurl.com", arr[1].getPath());
        
    }
    

    @org.junit.Test
    public void testSaveFavorite_NoExistingFavorites_JustOneProperty() {
        System.out.println("testSaveFavorite_NoExistingFavorites_JustOneProperty");

        //setup
        final Properties testProperties = new Properties();
                
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        instance.setPropertiesLoader(new PropertiesLoaderStub(){
            @Override
            public Properties loadProperties(String filename) {
                return testProperties;
            }

            @Override
            public void saveProperties(String filename, Properties props) throws IOException {
                testProperties.clear();
                testProperties.putAll(props);
            }
        });
        
        //act
        instance.SaveFavorite(new LocalTime(8,00), "www.testurl.com");

        //assert
        assertEquals(1, testProperties.keySet().size());
        String str = testProperties.getProperty("8:00 AM|www.testurl.com");
        assertNotNull("the one property should exist", str);
        assertEquals("", str);        
    }
    
    @org.junit.Test
    public void testSaveFavorite_OneExistingFavorite_AppendsNewFavorite() {
        System.out.println("testSaveFavorite_OneExistingFavorite_AppendsNewFavorite");

        //setup
        //setup
        final Properties testProperties = new Properties();
        testProperties.setProperty("8:00 AM|www.testurl.com", "");
                
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        instance.setPropertiesLoader(new PropertiesLoaderStub(){
            @Override
            public Properties loadProperties(String filename) {
                return testProperties;
            }

            @Override
            public void saveProperties(String filename, Properties props) throws IOException {
                testProperties.clear();
                testProperties.putAll(props);
            }
        });

        //act
        instance.SaveFavorite(new LocalTime(12,00), "www.test2.com");
        
        //assert
        assertEquals(2, testProperties.keySet().size());
        
        String str = testProperties.getProperty("8:00 AM|www.testurl.com");
        assertNotNull("the old property should still exist", str);
        
        str = testProperties.getProperty("12:00 PM|www.test2.com");
        assertNotNull("the new property should also exist", str);
    }

    /**
     * Test of DeleteFavorite method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testDeleteFavorite() {
        System.out.println("DeleteFavorite");
        FavoriteAlarm favorite = null;
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        boolean expResult = false;
        boolean result = instance.DeleteFavorite(favorite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CreateAlarm method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testCreateAlarm() {
        System.out.println("CreateAlarm");
        FavoriteAlarm favorite = null;
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        SetAlarm expResult = null;
        SetAlarm result = instance.CreateAlarm(favorite);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Serialize method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testSerialize() {
        System.out.println("Serialize");
        FavoriteAlarm alarm = null;
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        String expResult = "";
        String result = instance.Serialize(alarm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Deserialize method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testDeserialize() {
        System.out.println("Deserialize");
        String str = "";
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        FavoriteAlarm expResult = null;
        FavoriteAlarm result = instance.Deserialize(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
