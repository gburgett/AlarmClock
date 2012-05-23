package alarmclock.ServiceImplementations;

import alarmclock.services.ProcessStarter;
import java.io.IOException;

/**
 * This is the real implementation of the ProcessStarter interface.  It does
 * what the interface said it would do, that is, execute the resource at the 
 * given path.
 * @author Gordon
 */
public class RealProcessStarter implements ProcessStarter {

    @Override
    public void runFile(String path) 
            throws IOException
    {
        /*
         * We run the file by executing a command line argument.  This is like
         * typing "cmd" into the search bar in the start menu, then typing
         * "start www.pandora.com" into the command prompt.
         */
        
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c \"start " + path + "\"");
        pb.start();
    }
}
