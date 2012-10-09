package alarmclock.ServiceImplementations;

import alarmclock.services.PropertiesLoader;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A properties loader which loads properties from the local disk.
 * This PropertiesLoader has a file from which it loads and saves Properties
 * objects.  This forms the basis for our persistent storage.
 *
 * This class Does Something.
 * @author gordon
 */
public class LocalDiskPropertiesLoader implements PropertiesLoader{

    /**
     * One lock for read/writing all files, so we don't have to worry
     * about reference counting or anything.
     *
     * This field is used to synchronize access between threads.
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void saveProperties(String filename, Properties props)
        throws IOException
    {
        //load the default settings;
        OutputStream stream = null;
        try{
            //Lock so other threads executing the same code don't try to do
            //something else with this file while we're writing it.
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

            stream = new java.io.FileOutputStream(propsFile);

                //save the settings
            props.store(stream, "");

        }finally{
            //ALWAYS ALWAYS ALWAYS unlock your locks and close your streams in
            //a finally block.  A finally block will always be executed
            //regardless of what happens inside the try block, even if there
            //is a massive error.

            lock.writeLock().unlock();
            if(stream != null)
                stream.close();
        }
    }

    @Override
    public Properties loadProperties(String filename)
    {
        try{
            //Lock so other threads don't try to write to this file while we're
            //reading from it.  Other threads can simultaneously read since we're
            //not modifying the file.
            lock.readLock().lock();
            Properties defaultProps = new Properties();

            {   //arbitrarily limit the scope of the variables inside here
                    //load the default settings;
                InputStream stream = null;
                try{
                    try{
                            //This gets a stream from an internal resource file
                            //which exists inside the JAR.
                        stream = LocalDiskPropertiesLoader.class.getResourceAsStream(filename);

                            //load defaults if we can, but if not then fail silently
                        if(stream != null)
                            defaultProps.load(stream);
                    }
                    finally
                    {
                        //ALWAYS ALWAYS ALWAYS clean up your resources
                        //by closing streams in a finally block
                        if(stream != null)
                            stream.close();
                    }
                }catch(IOException ex){
                    //default props is corrupted or doesn't exist, clear it
                    defaultProps.clear();
                }
            }//end arbitrary variable scope

                //create the appSetting using the default properties
            Properties ret = new Properties(defaultProps);

            { //limit the scope so we can redeclare 'stream'

                    //load the default settings;
                InputStream stream = null;
                try{
                    try{
                            //open the stream from the settings file
                        File propsFile = new File(filename);
                            //if the file exists
                        if(propsFile.exists()){
                            stream = new FileInputStream(propsFile);

                                //load the settings
                            ret.load(stream);
                        }else{
                            ret = defaultProps;
                        }
                    }finally{
                        //ALWAYS ALWAYS ALWAYS clean up your resources
                        //by closing streams in a finally block
                        if(stream != null)
                            stream.close();
                    }
                }catch(IOException ex){
                    //the props file is corrupted or doesn't exist,
                    //return an empty properties file.
                    ret.clear();
                }

            }//end arbitrary variable scope

            return ret;
        }finally{
            //ALWAYS ALWAYS ALWAYS unlock your locks inside a finally block
            lock.readLock().unlock();
        }

    }//end loadProperties

}
