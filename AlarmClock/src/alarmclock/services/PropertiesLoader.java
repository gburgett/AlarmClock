/*
 * Copyrighted © 2011 by Simply Trackable LLC. All rights reserved.
 * This document may not be reproduced in whole or in part without the prior,
 * written consent of Simply Trackable LLC.
 */
package alarmclock.services;


import java.io.IOException;
import java.util.Properties;

/**
 * A service which loads properties files from a datastore.
 * @author gordon
 */
public interface PropertiesLoader {

    /**
     * Save the given properties to the properties file with the given filename.
     * <p/>
     * ex.<br/>
     * {@code
     * service.saveProperties("MyProps.properties", myProps);
     * }
     * @param filename the file in which to save the properties
     * @param props the properties to save
     * @throws IOException if an error occured while saving.
     */
    public void saveProperties(String filename, Properties props)
            throws IOException;

    /**
     * Load the given properties from the given file name.  Looks first for
     * a file on the classpath to load as the defaults, then a file in the root
     * directory to load the actual values.
     * <p/>
     * This method fails silently, returning
     * an empty Properties object for files that don't exist or are corrupted.
     * <p/>
     * ex.<br/>
     * {@code
     * Properties myProps = service.loadProperties("MyProps.properties");
     * }
     * @param filename the name of the properties file to load
     * @return the loaded properties, or an empty properties object if the file didn't exist.
     */
    public Properties loadProperties(String filename);

}
