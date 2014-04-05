package gui;

import androiddeodexer.ProgressBarListener;
import androiddeodexer.ProgressBarEvent;
import androiddeodexer.ScriptCLEAR;
import androiddeodexer.ScriptDEODEX;
import androiddeodexer.ScriptPULL;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Luis Peregrina
 */
public class ProgressBar extends JDialog implements ProgressBarListener {
    //Variables
    final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public float progressCounter = 0;
    public float progressIncrement = 0;
    private String script_s;
    
    //The scripts
    ScriptCLEAR scriptC = null;
    ScriptPULL scriptP = null;
    ScriptDEODEX scriptD = null;
    
    //Changes label text, adds "i" to the progressCounter
    public void Add(String msg){
        progressCounter = progressCounter + progressIncrement;
        pb_progress.setValue(Math.round(progressCounter));
        lbl_text.setText(msg);
    }
    
    //Toggles clickability of the OK button
    public void toggleOK(boolean toggle){
        btn_ok.setEnabled(toggle);
    }
    
    //For a reset
    private void defaultInit(String s, boolean reset){
        script_s = s;
        scriptC = null;
        scriptP = null;
        scriptD = null;
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocation((screenWidth/2)-(getBounds().width/2), 
                (screenHeight/2)-(getBounds().height/2));
        setIconImage(new ImageIcon(getClass().getClassLoader()
                .getResource("resources/icons/main.png")).getImage());
        if(!reset){
            initComponents();
        }
        
        toggleOK(false);
    }
        
    private void defaultFinish(){
        setVisible(true);
    }
    
    //For PULL and CLEAR scripts
    public ProgressBar(String script_s) {
        defaultInit(script_s, false);
        
        if(script_s.equals("clear_apk")){
            scriptC = new ScriptCLEAR("app");
        }
        else if(script_s.equals("clear_framework")){
            scriptC = new ScriptCLEAR("framework");
        }
        else if(script_s.equals("pull_apk")){
            scriptP = new ScriptPULL("app");
        }
        else if(script_s.equals("pull_framework")){
            scriptP = new ScriptPULL("framework");
        }

        if(script_s.contains("clear")){
            scriptC.addProgressBarListener(this);
        }
        else{
            scriptP.addProgressBarListener(this);
            
            addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        scriptP.killServer();
                    }

                });
        }
        
        //Finish
        defaultFinish();
    }
    
    //For DEODEX scripts
    public ProgressBar(JFrame owner, String[] apks, String api, int compression, String ext) {
        defaultInit("", false);
        
        //Start the scripts
        scriptD = new ScriptDEODEX(apks, api, compression, ext);
        
        //Add listener for the script
        scriptD.addProgressBarListener(this);
        
        //Finish
        defaultFinish();
    }
    
    //HERE WE DECIDE WHAT TO DO WITH THE EVENTS RECIEVED
    public void somethingHappened(ProgressBarEvent pve){
        //Action=message, Message=This doesnt increment the progress bar, see "add"
        if(pve.getAction().equals("message")){
            lbl_text.setText(pve.getMessage());
        }
        //Action=error, Message=Unable to run program
        else if(pve.getAction().equals("error")){
            setIconImage(new ImageIcon(getClass().getClassLoader()
                    .getResource("resources/icons/error.png")).getImage());
            setTitle("Error");
            lbl_text.setText(pve.getMessage());
            toggleOK(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        //Action=done, Message=Done copying files
        else if(pve.getAction().equals("done")){
            setIconImage(new ImageIcon(getClass().getClassLoader()
                    .getResource("resources/icons/ok.png")).getImage());
            setTitle("Done");
            pb_progress.setValue(100);
            lbl_text.setText(pve.getMessage());
            toggleOK(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        //Action=reset, Message=0.15 clear_apk
        else if(pve.getAction().equals("reset")){
            progressIncrement = Float.parseFloat(pve.getMessage());
            setTitle("Please wait...");
            //defaultInit(script_s, true);
        }
        //Action=add, Message=Making directories
        else if(pve.getAction().equals("add")){
            Add(pve.getMessage());
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pb_progress = new javax.swing.JProgressBar();
        btn_ok = new javax.swing.JButton();
        lbl_text = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setResizable(false);

        btn_ok.setText("OK");
        btn_ok.setEnabled(false);
        btn_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_okActionPerformed(evt);
            }
        });

        lbl_text.setText("Please wait...");
        lbl_text.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pb_progress, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(lbl_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_text, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pb_progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ok)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_okActionPerformed
        // Exit
        this.dispose();
    }//GEN-LAST:event_btn_okActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ok;
    private javax.swing.JLabel lbl_text;
    private javax.swing.JProgressBar pb_progress;
    // End of variables declaration//GEN-END:variables
}
