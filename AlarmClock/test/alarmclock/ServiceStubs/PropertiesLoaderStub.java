package alarmclock.ServiceStubs;

import alarmclock.services.PropertiesLoader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is a stub of the PropertiesLoader interface.  What that means is
 * this class fills the hole of a PropertiesLoader but doesn't actually do anything.
 * For unit tests we create an anonymous subclass of this where we override what
 * we actually want to do.
 * @author Gordon
 */
public class PropertiesLoaderStub implements PropertiesLoader{

    @Override
    public void saveProperties(String filename, Properties props) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Properties loadProperties(String filename) {
        throw new UnsupportedOperationException("Not supported");
    }
    
}
