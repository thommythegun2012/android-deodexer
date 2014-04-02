package androiddeodexer;

import gui.Main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Luis Peregrina
 */
public class AndroidDeodexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Look and feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        //Get variables
        
        //Main window
        Main mainFrame = new Main();
        
    }
    
}
