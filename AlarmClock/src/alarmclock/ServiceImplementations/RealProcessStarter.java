package alarmclock.ServiceImplementations;

import alarmclock.services.ProcessStarter;
import java.io.IOException;

/**
 *
 * @author Gordon
 */
public class RealProcessStarter implements ProcessStarter {

    @Override
    public void runFile(String path) 
            throws IOException
    {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c \"start " + path + "\"");
        pb.start();
    }
}
