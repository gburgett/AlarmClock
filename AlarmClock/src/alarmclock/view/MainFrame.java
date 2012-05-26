/*
 * MainFrame.java
 *
 * Created on Feb 9, 2012, 12:07:46 AM
 */
package alarmclock.view;

import alarmclock.models.FavoriteAlarm;
import alarmclock.models.SetAlarm;
import alarmclock.services.AlarmStarter;
import alarmclock.services.FavoriteAlarmService;
import alarmclock.services.ProcessStarter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.joda.time.DateTime;

/**
 * The MainFrame displays the data and responds to events.  Because this is
 * written in the Swing framework, it must also be the Controller, that is,
 * the part that responds to user input and changes the View.  More modern
 * UI frameworks separate out the Controller and the View aspects.
 * 
 * This class Does Something.  It is a View and also a Controller.
 * @author Gordon
 */
public class MainFrame extends javax.swing.JFrame {

    //<editor-fold desc="fields">    
    /**
     * This is the sorted collection we are using to hold the current alarm panels.
     * We enforce the ordering by providing it with a Comparator.  This Comparator
     * specifies that two AlarmPanels should be compared by the Time of their Alarms.
     * When the SortedSet wants to sort itself it will call the compare method
     * of this comparator.
     */
    private SortedSet<AlarmPanel> alarms = new java.util.TreeSet<AlarmPanel>(
            //The Comparator is defined as an anonymous class right here inline
            new Comparator<AlarmPanel>(){
                @Override
                public int compare(AlarmPanel o1, AlarmPanel o2) {
                    return o1.getAlarm().getTime().compareTo(o2.getAlarm().getTime());
                }
            });
    
    /**
     * This is simply a File choosing dialog that we can pop up when the user
     * hits the choose button.
     */
    private final JFileChooser fileChooser = new JFileChooser();
    
    //this timer is just going to invoke Update to update the display clock, it's not
    //going to do any important timing, all that will happen in TimerAlarmStarter
    javax.swing.Timer updateTimer;
    //</editor-fold>
    
    //<editor-fold desc="services">
    /*
     * Here are all the injected services.  These services will be used by
     * the MainFrame controller to perform actions, namely creating saving and 
     * setting Alarms.  These service objects are all objects that Do Something.
     * They are not created by the MainFrame itself, but rather are injected
     * so that the main frame can be more easily tested.
     * 
     * Injected Services only need a setter because they are only used internally.
     */
    
    private ProcessStarter processStarter;
    public void setProcessStarter(ProcessStarter pStarter) {
        this.processStarter = pStarter;
    }
    
    private AlarmStarter alarmStarter;
    public void setAlarmStarter(AlarmStarter starter){
        this.alarmStarter = starter;
    }
    
    private FavoriteAlarmService favoritesService;
    public void setFavoritesService(FavoriteAlarmService favoritesService){
        this.favoritesService = favoritesService;
    }
    //</editor-fold>
    
    /** The MainFrame constructor
     *  just sets up default stuff the frame needs in order to run
     */
    public MainFrame() {
        initComponents();
        
        //This just tells the frame to exit the program when the main frame closes.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is called to initialize the class AFTER all the injected
     * properties have been set.  We cannot call this in the constructor because
     * we require the injected services, but we must do this before we do anything
     * with the MainFrame.
     */
    public void init(){
                
        this.loadFavoriteAlarms();
        
        //Set up the timer to call update every 1 second
        updateTimer = new javax.swing.Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.update();
            }
        });
        updateTimer.start();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setSpinner = new javax.swing.JSpinner();
        exePath = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        setAlarmsPanel = new javax.swing.JPanel();
        chooseButton = new javax.swing.JButton();
        setButton = new javax.swing.JButton();
        errorText = new javax.swing.JLabel();
        CurrentTime = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        favoritesPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        testButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setSpinner.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        setSpinner.setModel(new javax.swing.SpinnerDateModel());
        setSpinner.setEditor(new javax.swing.JSpinner.DateEditor(setSpinner, "h:mm a"));

        exePath.setMinimumSize(new java.awt.Dimension(200, 20));
        exePath.setPreferredSize(new java.awt.Dimension(200, 20));
        exePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exePathActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 100));

        setAlarmsPanel.setMaximumSize(new java.awt.Dimension(65535, 65535));
        setAlarmsPanel.setLayout(new javax.swing.BoxLayout(setAlarmsPanel, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(setAlarmsPanel);

        chooseButton.setText("Choose");
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButtonActionPerformed(evt);
            }
        });

        setButton.setText("Set");
        setButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setButtonActionPerformed(evt);
            }
        });

        errorText.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        errorText.setForeground(new java.awt.Color(255, 51, 51));
        errorText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        CurrentTime.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        CurrentTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CurrentTime.setText("12:00 AM");

        favoritesPanel.setLayout(new javax.swing.BoxLayout(favoritesPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(favoritesPanel);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        testButton.setText("Test");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                            .addComponent(errorText, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(setButton, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(exePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(setSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(chooseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(testButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                    .addComponent(CurrentTime, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(CurrentTime, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(setSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exePath, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooseButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveButton)
                            .addComponent(setButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(errorText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void exePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exePathActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_exePathActionPerformed

private void chooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButtonActionPerformed
    //This is the event handler for when the choose button is clicked
    
    this.errorText.setText("");
    
    //Open the file chooser dialog
    int returnVal = fileChooser.showOpenDialog(this);
    
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        //If the user approved a file, get it and set the text.
        File file = fileChooser.getSelectedFile();
        
        //perform a little processing on the path to make sure parts with spaces are
        //enclosed in quotation marks
        String[] path = file.getPath().split("\\\\");
        StringBuilder sb = new StringBuilder();
        int i;
        for(i = 0; i < path.length-1; i++){
            if(path[i].contains(" ")){
                //gotta add quotes if part of the path contains spaces
                sb.append("\"").append(path[i]).append("\"");
            }
            else
                sb.append(path[i]);
            
            sb.append("\\");
        }
        if(path[i].contains(".")){
            String[] splitOnDot = path[i].split("\\.");
            if(splitOnDot[0].contains(" "))
                //gotta add quotes if part of the path contains spaces
                sb.append("\"").append(splitOnDot[0]).append("\"");
            else
                sb.append(splitOnDot[0]);
            sb.append(".").append(splitOnDot[1]);
        }
        else
            sb.append(path[i]);
        
        //set the text box to the new value
        this.exePath.setText(sb.toString());
    }
}//GEN-LAST:event_chooseButtonActionPerformed

private void setButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setButtonActionPerformed
    this.errorText.setText("");
    
    //Set a new alarm
    DateTime almTime = new DateTime(this.setSpinner.getValue());
    almTime = new DateTime().millisOfDay().setCopy(almTime.getMillisOfDay());
    
    if(almTime.isBeforeNow())
        //they probably meant tomorrow
        almTime = almTime.dayOfMonth().addToCopy(1);
    
    this.setAlarm(almTime, this.exePath.getText());
    
}//GEN-LAST:event_setButtonActionPerformed

private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
    //Save a new favorite alarm
    this.setFavorite(new DateTime(this.setSpinner.getValue()), this.exePath.getText());
}//GEN-LAST:event_saveButtonActionPerformed

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
        this.errorText.setText("");
        
        String path = this.exePath.getText();
        
        if(path == null || path.trim().isEmpty()){
            this.errorText.setText("No path to test");
            return;
        }
        
        try {
            //execute the given file immediately
            this.processStarter.runFile(path);
            
        } catch (IOException ex) {
            //there was an error, show it in the error window
            this.errorText.setText(ex.toString());
        }
    }//GEN-LAST:event_testButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CurrentTime;
    private javax.swing.JButton chooseButton;
    private javax.swing.JLabel errorText;
    private javax.swing.JTextField exePath;
    private javax.swing.JPanel favoritesPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton saveButton;
    private javax.swing.JPanel setAlarmsPanel;
    private javax.swing.JButton setButton;
    private javax.swing.JSpinner setSpinner;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables


    /**
     * This method is called by the timer every 1 second to update the
     * clock.
     */
    private void update(){
        //We have to pass it back to the EventQueue because it modifies a Swing element.
        //Since this method is called by a timer we can't expect it to be on the same
        //thread as the event queue, so we tell the event queue to invoke this code
        //when it gets a chance.
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                DateTime now = new DateTime(System.currentTimeMillis());
                MainFrame.this.CurrentTime.setText(now.toString("MMM d, yyyy h:mm:ss a"));                 
            }
        });     
        
    }
    
        /**
     * This method is called when a new FavoriteAlarm gets set.
     * @param time The TimeOfDay for the favorite alarm
     * @param exePath The Path to use for the alarm
     * @return A Panel that displays the new FavoriteAlarm, or null if it couldn't
     * be saved.
     */
    private FavoriteAlarmPanel setFavorite(DateTime time, String exePath){
        //perform some initial validation
        if(exePath.trim().isEmpty())
            return null;
        
        if(time == null)
            return null;
        
        //Create the new FavoriteAlarm and save it using the favoritesService
        FavoriteAlarm alm = this.favoritesService.SaveFavorite(time.toLocalTime(), exePath);
        
        //Create the new display panel and hook up its events
        final FavoriteAlarmPanel fave = new FavoriteAlarmPanel(alm);
        this.hookupFavorite(fave);
        
        //Add it to our FavoriteAlarms container and turn it on
        this.favoritesPanel.add(fave);
        fave.setVisible(true);
        this.favoritesPanel.repaint();
        
        return fave;
    }
    
    /**
     * This method is called to add event listeners to a newly created FavoriteAlarmPanel.
     * @param fave the FavoriteAlarmPanel to which the event listeners should be added.
     */
    private void hookupFavorite(final FavoriteAlarmPanel fave){
        //we'll create and add the listener inline using an anonymous class
        fave.addFavoriteAlarmListener(new FavoriteAlarmPanel.FavoriteAlarmListener() {

            @Override
            public void RemoveFavorite(FavoriteAlarmPanel.FavoriteAlarmEventObject favorite) {
                //This event is fired when the Remove button is clicked.
                MainFrame.this.favoritesService.DeleteFavorite(favorite.getFavorite());
                MainFrame.this.favoritesPanel.remove(fave);
                MainFrame.this.favoritesPanel.repaint();
                MainFrame.this.repaint();
            }

            @Override
            public void StartFavorite(FavoriteAlarmPanel.FavoriteAlarmEventObject favorite) {
                MainFrame.this.startFavoriteAlarm(favorite.getFavorite());
            }
        });
    }
    
    
    /**
     * Starts a FavoriteAlarm when a FavoriteAlarm panel fires the StartFavorite
     * event.
     * @param alarm The FavoriteAlarm that should be started.
     */
    private void startFavoriteAlarm(FavoriteAlarm alarm){
                   
        //call into the favorites service to create an Alarm from the FavoriteAlarm
        SetAlarm setAlarm = this.favoritesService.CreateAlarm(alarm);
        
        this.setAlarm(setAlarm);
    }
    
    private SetAlarm setAlarm(DateTime time, String exePath){
        //call into the alarm starter service to create an Alarm for the given time and path
        final SetAlarm alm = this.alarmStarter.CreateAlarm(time, exePath);

        this.setAlarm(alm);
        
        return alm;
    }
    
    private SetAlarm setAlarm(final SetAlarm alm){
        //Create a new Panel view to display this alarm
        final AlarmPanel panel = new AlarmPanel();
        panel.setAlarm(alm);
        
        try {
            //Try to start this alarm
            this.alarmStarter.StartAlarm(alm, new Runnable(){
                @Override
                public void run() {
                    //When the alarm goes off, run this code
                    MainFrame.this.onAlarmFinished(panel, alm);
                }                
            });
            
        } catch (Exception ex) {
            //Log the error, and set the errorText display
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            this.errorText.setText("Can't start alarm: " + ex.toString());
            return null;
        }
        
        //Make sure we're listening in case the user clicks the cancel button
        panel.addCancelAlarmListener(new AlarmPanel.CancelAlarmListener(){
            @Override
            public void alarmCancelled(AlarmPanel.CancelAlarmEventObject alarm) {
                //remove the alarm from the display
                MainFrame.this.alarms.remove((AlarmPanel)alarm.getSource());
                MainFrame.this.updateAlarmsPanel();                            
                panel.removeCancelAlarmListener(this);
                
                //cancel the running alarm task
                MainFrame.this.alarmStarter.CancelAlarm(alarm.getAlarm());
            }            
        });
        
        //Add the Alarm display panel to our Alarms panel
        this.alarms.add(panel);
        
        //turn on the new alarm panel
        panel.setVisible(true);
        this.updateAlarmsPanel();
        
        return alm;
    }

    /**
     * This method gets executed when an Alarm goes off.  It is called by
     * the alarmStarter service when the alarm goes off.
     * @param panel The AlarmPanel that housed the alarm
     * @param alm The Alarm that went off
     */
    private void onAlarmFinished(final AlarmPanel panel, final SetAlarm alm){
        try {
            //Execute the file
            this.processStarter.runFile(alm.getPath());
        } catch (final IOException ex) {
            //Uh oh!  Log it
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    
            //Can't guarantee this code is running on the same thread as the EventQueue
            //so we do this invokeLater thing again
            java.awt.EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    //Set the errorText display to show the error
                    MainFrame.this.errorText.setText("Could not start " + 
                            alm.getPath() + " because: " +
                            ex.getMessage());
                }        
            });
            return;
        }
        
        //Can't guarantee this code is running on the same thread as the EventQueue
        //so we do this invokeLater thing again
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                //Remove the alarm now that it's finished
                MainFrame.this.alarms.remove(panel);
                MainFrame.this.updateAlarmsPanel();
            }
        });     
    }
    
   
    /**
     * This is called when there's a change to the AlarmsPanel container.  It
     * removes and re-adds all the alarms in the order specified by the SortedSet
     * of alarm panels.  It should always be called on the EventQueue thread because
     * it modifies a Swing component.
     */
    private void updateAlarmsPanel(){
        this.setAlarmsPanel.removeAll();
        for(AlarmPanel p : this.alarms){
            this.setAlarmsPanel.add(p);            
        }
        this.setAlarmsPanel.repaint();
    }
    
    /**
     * This method is called on startup to load up the favorite alarms out of
     * the favorites service, and create display panels for all of them.
     */
    private void loadFavoriteAlarms(){
                
        
        for(FavoriteAlarm alm : this.favoritesService.getFavorites()){
            FavoriteAlarmPanel almPanel = new FavoriteAlarmPanel(alm);
            
            this.hookupFavorite(almPanel);
            this.favoritesPanel.add(almPanel);
            almPanel.setVisible(true);            
        }
        
        this.favoritesPanel.repaint();
    }
}
