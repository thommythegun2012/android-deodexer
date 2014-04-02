
package androiddeodexer;

import gui.ProgressBar;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
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
        one = (1f/(8f*totalFiles))*100f;
        
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
                    System.out.println("Cancelled pulling.");
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
            directoryChange("framework");
            clean(apk);
            directoryChange(".."+sep);
            
            progress.Add(one, "Copying required files...");
            //Copy Smali & Baksmali if they dont exist.
            if(!(new File("framework"+sep+"smali.jar")).exists() || 
                    !(new File("framework"+sep+"smali.jar")).exists()){
                directoryChange("framework"+sep+"..");
                fileCopy("resources"+sep+"smali.jar","framework"+sep+"smali.jar");
                fileCopy("resources"+sep+"baksmali.jar","framework"+sep+"baksmali.jar");
                
            }
            if(ext.equals("apk")){
                //Copy apks\SystemUI.odex to framework\SystemUI.odex
                fileCopy("app"+sep+ apk+".odex", "framework"+sep+ apk+".odex");
            }
            
            progress.Add(one, "Disassembling...");
            //Dissasemble
            execute("cd framework && java -Xmx512m -jar baksmali.jar -x " + apk + ".odex -a" + api);
            progress.Add(one, "Assembling...");
            //Assemble
            execute("cd framework && java -Xmx512M -jar smali.jar out"+sep+" -o classes.dex");
            
            
            progress.Add(one, "Creating directories...");
            directoryChange("deodexed");
            directoryChange(".."+sep+"buildDEODEX");
            
            progress.Add(one, "Copying file...");
            //Copy from ..\framework\classes.dex to buildDEODEX\classes.dex
            fileCopy(".."+sep+"framework"+sep+"classes.dex", "classes.dex");
            
            progress.Add(one, "Unzipping file...");
            if(ext.equals("apk")){
                //Unzip ..\apks\SystemUI.apk to buildDEODEX\
                fileUnzip(".."+sep+"app"+sep + apk + "." + ext, ".");
            }
            else{
                //Unzip ..\framework\SystemUI.apk to buildDEODEX\
                fileUnzip(".."+sep+"framework"+sep + apk + "." + ext, ".");
            }
            
            progress.Add(one, "Zipping directory...");
            //Zip all buildDEODEX\ to ..\deodexed\Done.apk
            directoryZip(".."+sep+"deodexed"+sep+apk + "." + ext, ".", compression);
            //Delete all temp files
            directoryChange(".."+sep+"framework");
            clean(apk);
            echo(apk + " deodexed.");
            
            directoryChange(".."+sep);
            progress.Add(one, "Done: " + apk + " deodexed.");
            
            //Open file exlplorer on the finished product
            directoryOpen("deodexed");

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
        fileDelete("out"+sep);
        fileDelete("classes.dex");
        fileDelete(".."+sep+"buildDEODEX"+sep);
        if(ext.equals("apk")){
            fileDelete(apk+".odex");
        }
    }
    
    
    
}

