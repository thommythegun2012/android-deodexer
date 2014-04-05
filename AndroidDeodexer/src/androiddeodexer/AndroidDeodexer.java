package androiddeodexer;

import gui.Main;
import java.io.File;
import java.util.Arrays;

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
        try{
            install();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static void install() throws Exception{
        System.out.println("Verifying necessary files...");
        
        
        
        SysUtils u = new SysUtils();
        int os = u.OSCommands();
        if(!new File(u.CWD+"resources"+u.sep+u.adb).exists()){
            System.out.println("Files missing, unpacking...");
            u.directoryMake("resources");
            u.fileCopyFromJar("/resources/"+u.adb, "resources"+u.sep+u.adb );
            if(os==0){
                //Windows needs these additional files
                u.fileCopyFromJar("/resources/AdbWinApi.dll", "resources/AdbWinApi.dll");
                u.fileCopyFromJar("/resources/AdbWinUsbApi.dll", "resources/AdbWinUsbApi.dll");
            }
            else{
                //Mac and Unix need to change file permissions to Executable
                u.execute(".", Arrays.asList("chmod","0755", u.CWD + "resources" + u.sep + u.adb));
            }
        }
        System.out.println("Done with nessesary files.");
    }
    
}
