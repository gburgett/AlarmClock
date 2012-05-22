/*
 * Copyrighted © 2011 by Simply Trackable LLC. All rights reserved.
 * This document may not be reproduced in whole or in part without the prior,
 * written consent of Simply Trackable LLC.
 * 
 */
package alarmclock.ServiceImplementations;

import alarmclock.services.PropertiesLoader;
import alarmclock.services.PropertiesLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A properties loader which loads properties from the local disk.
 * @author gordon
 */
public class LocalDiskPropertiesLoader implements PropertiesLoader{

    /**
     * One lock for read/writing all files, so we don't have to worry
     * about reference counting or anything.
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void saveProperties(String filename, Properties props)
        throws IOException
    {
        //load the default settings;
        OutputStream stream = null;
        try{
            lock.writeLock().lock();
                //open the stream from the settings file
            File propsFile = new File(filename);
                //if the file exists
            if(!propsFile.exists()){
                    //get the directory path
                File dir = propsFile.getParentFile();
                if(dir != null){
                    if(!dir.exists()) 
                        dir.mkdirs();
                    else if (!dir.isDirectory()){
                        throw new IOException("File has non-directory parent");
                    }
                }
                
                propsFile.createNewFile();
            }

            stream =
                new java.io.FileOutputStream(propsFile);
            if(stream == null)
                throw new java.io.IOException("Unable to load settings");

                //save the settings
            props.store(stream,"No Comments");

        }finally{
            lock.writeLock().unlock();
            if(stream != null)
                stream.close();
        }
    }

    @Override
    public Properties loadProperties(String filename)
    {
        try{
            lock.readLock().lock();
            Properties defaultProps = new Properties();

            {   //limit the scope of the code in here
                    //load the default settings;
                InputStream stream = null;
                try{
                    try{
                            //get the stream from the classpath
                        stream =
                            LocalDiskPropertiesLoader.class.getResourceAsStream(filename);

                            //load defaults if we can, but if not then fail silently
                        if(stream != null)
                            defaultProps.load(stream);
                    }finally{
                            if(stream != null)
                                stream.close();
                    }
                }catch(IOException ex){
                    //default props is corrupted or doesn't exist, clear it
                    defaultProps.clear();
                }
            }

                //create the appSetting using the default properties
            Properties ret = new Properties(defaultProps);
            {
                    //load the default settings;
                InputStream stream = null;
                try{
                    try{
                            //open the stream from the settings file
                        File propsFile = new File(filename);
                            //if the file exists
                        if(propsFile.exists()){
                            stream =
                                new FileInputStream(propsFile);
                            if(stream == null)
                                throw new java.io.IOException("Unable to load settings");

                                //load the settings
                            ret.load(stream);
                        }else{
                            ret = defaultProps;
                        }
                    }finally{
                        if(stream != null)
                            stream.close();
                    }
                }catch(IOException ex){
                    //the props file is corrupted or doesn't exist,
                    //return an empty properties file.
                    ret.clear();
                }


            }

            return ret;
        }finally{
            lock.readLock().unlock();
        }

    }//end loadProperties

}
