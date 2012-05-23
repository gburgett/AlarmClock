/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alarmclock.services;

import java.io.IOException;

/**
 * This interface defines the contract for a service that will execute
 * a given file which resides at the given URL.  The URL can be a local file path
 * or an Internet location.
 * 
 * This interface defines the contract for classes which Do Something.
 * @author Gordon
 */
public interface ProcessStarter {
    /**
     * Opens the URL at the given path.  This path can be a local file or an
     * Internet location.
     * @param path The URL of the resource to execute.
     * @throws IOException if there was an error opening the file or starting 
     * the resource.
     */
    public void runFile(String path)
            throws IOException;     
}
