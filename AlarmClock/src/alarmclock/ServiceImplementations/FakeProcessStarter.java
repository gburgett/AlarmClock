package alarmclock.ServiceImplementations;

import alarmclock.services.ProcessStarter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a Fake process starter used for testing.  Instead of actually
 * running a file, it simply writes the path that would have been run to the console.
 * This is useful for debugging as we can just see what would have been run instead
 * of actually trying to run something that could be dangerous.
 * 
 * This class Does Something, albeit something else besides what the rest of the
 * program expects it to do :)
 * @author Gordon
 */
public class FakeProcessStarter implements ProcessStarter{

    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    
    @Override
    public void runFile(String path) {
        try {
            this.out.write("Running file: " + path);
            this.out.flush();
        } catch (IOException ex) {
            Logger.getLogger(FakeProcessStarter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
