/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.services;

import java.io.IOException;

/**
 *
 * @author Gordon
 */
public interface ProcessStarter {
    public void runFile(String path)
            throws IOException;     
}
