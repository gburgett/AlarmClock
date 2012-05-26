package alarmclock.ServiceImplementations;

import alarmclock.ServiceStubs.*;
import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import alarmclock.services.PropertiesLoader;
import alarmclock.testutils.Box;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

/**
 * This class contains JUnit tests for the FavoriteAlarmPropertiesService.
 * Since this class Does Something and is fairly complex, we need unit tests for it.
 * We make sure the unit tests are only testing this object by mocking or stubbing
 * any classes that this service relies upon.  This is why we use dependency injection.
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
            //This right here is exactly why dependency injection is important.
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
        
        //Set the stub - the stub is an anonymous class that overrides only what
        //we need to override.  The rest of it does nothing.
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
        //We are using a Box here because in order to access this variable from
        //the stub we need to declare it final.  The problem is if it is final
        //we can't set it, so we just set the property on the box object.
        final Box<Properties> testProperties = new Box<Properties>(new Properties());
                
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        //Set the stub - the stub is an anonymous class that overrides only what
        //we need to override.  The rest of it does nothing.
        instance.setPropertiesLoader(new PropertiesLoaderStub(){
            @Override
            public Properties loadProperties(String filename) {
                return testProperties.getValue();
            }

            @Override
            public void saveProperties(String filename, Properties props) throws IOException {
                testProperties.setValue(props);
            }
        });
        
        //act
        instance.SaveFavorite(new LocalTime(8,00), "www.testurl.com");

        //assert
        testProperties.VerifySet();
        
        Properties props = testProperties.getValue();
        
        assertEquals(1, props.keySet().size());
        String str = props.getProperty("8:00 AM|www.testurl.com");
        assertNotNull("the one property should exist", str);
        assertEquals("", str);        
    }
    
    @org.junit.Test
    public void testSaveFavorite_OneExistingFavorite_AppendsNewFavorite() {
        System.out.println("testSaveFavorite_OneExistingFavorite_AppendsNewFavorite");

        //setup
        //We are using a Box here because in order to access this variable from
        //the stub we need to declare it final.  The problem is if it is final
        //we can't set it, so we just set the property on the box object.
        final Box<Properties> testProperties = new Box<Properties>(new Properties());
        testProperties.getValue().setProperty("8:00 AM|www.testurl.com", "");
                
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        instance.setPropertiesLoader(new PropertiesLoaderStub(){
            @Override
            public Properties loadProperties(String filename) {
                return testProperties.getValue();
            }

            @Override
            public void saveProperties(String filename, Properties props) throws IOException {
                testProperties.setValue(props);
            }
        });

        //act
        instance.SaveFavorite(new LocalTime(12,00), "www.test2.com");
        
        //assert
        testProperties.VerifySet();
        
        Properties props = testProperties.getValue();
        
        assertEquals(2, props.keySet().size());
        
        String str = props.getProperty("8:00 AM|www.testurl.com");
        assertNotNull("the old property should still exist", str);
        
        str = props.getProperty("12:00 PM|www.test2.com");
        assertNotNull("the new property should also exist", str);
    }

    /*
     * I decided down here to switch from using custom stubs to using a Mocking
     * framework called Mockito.  This is how we test more complex services
     * that are not easily stubbed.
     */
    
    
    /**
     * Test of DeleteFavorite method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testDeleteFavorite() throws Exception {
        System.out.println("DeleteFavorite");
        
        //setup
        Properties testProperties = new Properties();
        testProperties.setProperty("8:00 AM|www.test.com", "");
                
        //setup mocks - we're going to create a mock of the PropertiesLoader class
        //and set it up so that when the service asks for the properties file
        //it will return our test properties
        PropertiesLoader loader = mock(PropertiesLoader.class);
        when(loader.loadProperties(FavoriteAlarmPropertiesService.FileName))
                .thenReturn(testProperties);
        
        //setup instance
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
                
        instance.setPropertiesLoader(loader);
        
        //act
        boolean result = 
            instance.DeleteFavorite(new FavoriteAlarm(new LocalTime(8, 00), "www.test.com"));
                
        //assert
        assertTrue("should have deleted the favorite", result);
        
        //use an argument captor to capture the argument that was given to the mock
        ArgumentCaptor<Properties> captor = ArgumentCaptor.forClass(Properties.class);        
        //verify that the mock was called once with the FileName and any instance of properties
        verify(loader, times(1)).saveProperties(
            anyString(), captor.capture());
        
        //assert on the actual value that was given to the mock
        Properties actual = captor.getValue();        
        assertEquals("shouldnt be any more properties", 0, actual.size());
    }

    /**
     * Test of CreateAlarm method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testCreateAlarm() {
        System.out.println("CreateAlarm");
        
        //setup
        FavoriteAlarm toConvert = new FavoriteAlarm(new LocalTime(8,00), "www.test.com");
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        SetAlarm alarm = instance.CreateAlarm(toConvert);
        
        // TODO review the generated test code and remove the default call to fail.
        assertTrue("Should always be set in the future", alarm.getTime().isAfterNow());
        assertTrue("Should be less than 24 hours in the future",
                alarm.getTime().isBefore(
                    new DateTime(System.currentTimeMillis()).plusDays(1)
                ));
        
        assertEquals(8, alarm.getTime().getHourOfDay());
        assertEquals(0, alarm.getTime().getMinuteOfHour());
        assertEquals("www.test.com", alarm.getPath());
    }

    /**
     * Test of Serialize method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testSerialize() {
        System.out.println("Serialize");
        
        //setup
        FavoriteAlarm alarm = new FavoriteAlarm(new LocalTime(9,00), "www.test.com");
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        //act
        String result = instance.Serialize(alarm);
        
        //assert
        String expResult = "9:00 AM|www.test.com";
        assertEquals(expResult, result);        
    }

    /**
     * Test of Deserialize method, of class FavoriteAlarmPropertiesService.
     */
    @org.junit.Test
    public void testDeserialize() {
        System.out.println("Deserialize");
        
        //setup
        String str = "10:00 AM|www.test.com";
        FavoriteAlarmPropertiesService instance = new FavoriteAlarmPropertiesService();
        
        FavoriteAlarm result = instance.Deserialize(str);
        
        FavoriteAlarm expected = new FavoriteAlarm(new LocalTime(10,00), "www.test.com");
        assertEquals(expected, result);        
    }
}
