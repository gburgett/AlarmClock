package alarmclock;

import alarmclock.ServiceImplementations.*;
import alarmclock.services.PropertiesLoader;
import alarmclock.view.MainFrame;

/**
 * This class is only used as the main entry point into the program.  It contains
 * the main method which initializes the program and sets it running.
 * @author Gordon
 */
public class Main {
    /**
     * The entry point for the program.  When you start the program this
     * is the first place it goes.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //Create the Main view frame which will show the program's UI
        final MainFrame mf = new MainFrame();
        
        //this pattern is called Dependency Injection, any services that the
        //UI relies on get injected so they can be replaced at whim.  This is
        //extremely useful for unit testing.
        PropertiesLoader localPropsLoader = new LocalDiskPropertiesLoader();
        
        FavoriteAlarmPropertiesService favoritesService = new FavoriteAlarmPropertiesService();
        favoritesService.setPropertiesLoader(localPropsLoader);
        mf.setFavoritesService(favoritesService);
        
        mf.setAlarmStarter(new TimerAlarmStarter());
        mf.setProcessStarter(new RealProcessStarter());

        //This is just how you start a Swing frame
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                //call the initialize method
                mf.init();
                //set the frame to visible
                mf.setVisible(true);
            }
        });
    }
}
