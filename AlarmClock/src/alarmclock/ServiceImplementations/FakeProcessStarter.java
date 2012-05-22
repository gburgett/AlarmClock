/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.ServiceImplementations;

import alarmclock.services.ProcessStarter;
import alarmclock.services.ProcessStarter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
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
