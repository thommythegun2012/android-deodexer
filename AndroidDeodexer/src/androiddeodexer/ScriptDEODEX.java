
package androiddeodexer;

import gui.ProgressBar;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

public class ScriptDEODEX extends SysUtils {
    
    private String[] apks;
    private String api;
    private int compression;
    private ProgressBar progress;
    private int totalFiles; 
    private String ext;
    private float one;
    boolean finished;
    
    public ScriptDEODEX(){
        //Error
    }
    
    public ScriptDEODEX(String[] apks, String api, int compression, String ext, JFrame parent){
        this.apks = apks;
        this.api = api;
        this.compression = compression;
        this.ext = ext;
        progress = new ProgressBar(parent);
        progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                .getResource("resources/icons/main.png")).getImage());
        progress.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        
        
        totalFiles = apks.length;
        one = (1f/(9f*totalFiles))*100f;
        
        //Start working
        final SwingWorker work1 = new SwingWorker<String, Integer>(){
            protected String doInBackground(){
                try{
                    WorkScript();
                }catch(Exception e){
                    progress.toggleOK(true);
                    progress.setTitle("Error.");
                    progress.message("<html>There was an error,<br/>check debug info.</html>");
                    progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                        .getResource("resources/icons/error.png")).getImage());
                    System.err.println (e.getLocalizedMessage());
                    e.printStackTrace();
                }
                return null;
            }
            protected void done(){
                progress.toggleOK(true);
                progress.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                progress.setTitle("Done.");
                progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                        .getResource("resources/icons/ok.png")).getImage());
                finished=true;
            }
        };
        work1.execute();
        
        
        //Cancel if JDialog is closed.
        progress.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                if(!finished){
                    work1.cancel(true);
                    System.out.println("Cancelled deodexing.");
                    clean();
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }
    
    
    public void WorkScript() throws Exception{
        for(String apk: apks){

            progress.Add(0, "Cleaning...");
            clean(apk);
            
            progress.Add(one, "Copying required files...");
            File smali = new File("framework"+sep+"smali.jar");
            File baksmali = new File("framework"+sep+"baksmali.jar");
            if(!smali.exists() || !baksmali.exists()){
                fileCopyFromJar("/resources/smali.jar", smali.getCanonicalPath());
                fileCopyFromJar("/resources/baksmali.jar", baksmali.getCanonicalPath());
                
            }
            if(ext.equals("apk")){
                //Copy apks\SystemUI.odex to framework\SystemUI.odex
                fileCopy("app"+sep+ apk+".odex", "framework"+sep+ apk+".odex");
            }
            
            progress.Add(one, "Disassembling...");
            execute("framework", Arrays.asList("java", "-Xmx512m","-jar", "baksmali.jar","-x", apk + ".odex",  "-a" + api));
            
            progress.Add(one, "Assembling...");
            execute("framework", Arrays.asList("java", "-Xmx512m","-jar", "smali.jar","out"+sep, "-o", "classes.dex"));
            
            progress.Add(one, "Creating directories...");
            directoryMake("deodexed");
            directoryMake("buildDEODEX");
            
            progress.Add(one, "Copying file...");
            //Copy from ..\framework\classes.dex to buildDEODEX\classes.dex
            fileCopy("framework"+sep+"classes.dex", "buildDEODEX"+sep+"classes.dex");
            
            progress.Add(one, "Unzipping file...");
            if(ext.equals("apk")){
                //Unzip ..\apks\SystemUI.apk to buildDEODEX\
                fileUnzip("app"+sep + apk + "." + ext, "buildDEODEX");
            }
            else{
                //Unzip ..\framework\SystemUI.apk to buildDEODEX\
                fileUnzip("framework"+sep + apk + "." + ext, "buildDEODEX");
            }
            
            progress.Add(one, "Zipping directory...");
            //Zip all buildDEODEX\ to ..\deodexed\Done.apk
            directoryZip("deodexed"+sep+apk + "." + ext, "buildDEODEX", compression);
            
            //Delete all temp files
            progress.Add(one, "Cleaning...");
            clean(apk);
            echo(apk + " deodexed.");
            
            if(apks.length>1){
                progress.Add(one, "<html>Done:<br/>All files deodexed.</html>");
            }
            else{
                progress.Add(one, "<html>Done:<br/>" + apk + " deodexed.</html>");
            }
            
            
            //Open file exlplorer on the finished product
            if(apk.equals(apks[apks.length-1])){
                directoryOpen("deodexed");
            }
            

        }
    }
    
    private void clean(){
        try{
            ext = "";
            clean("");
        }catch(Exception e){
            System.err.println("Error while cleaning.");
        }
        
    }
    
    private void clean(String apk) throws IOException{
        progress.Add(one, "Deleting files...");
        fileDelete(CWD+"framweork"+sep+"out"+sep);
        fileDelete(CWD+"framweork"+sep+"classes.dex");
        fileDelete(CWD+"buildDEODEX"+sep);
        if(ext.equals("apk")){
            fileDelete(CWD+"buildDEODEX"+sep+apk+".odex");
        }
    }
    
    
    
}
