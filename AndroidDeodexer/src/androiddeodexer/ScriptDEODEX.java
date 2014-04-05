package androiddeodexer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.SwingWorker;

public class ScriptDEODEX extends Script {
    
    private String[] apks;
    private String api;
    private int compression;
    private int totalFiles; 
    private String ext;
    private float increment;
    boolean finished;
    
    public ScriptDEODEX(){
        //Error
    }
    
    public ScriptDEODEX(String[] apks, String api, int compression, String ext){
        this.apks = apks;
        this.api = api;
        this.compression = compression;
        this.ext = ext;
        
        
        
        totalFiles = apks.length;
        increment = (1f/(9f*totalFiles))*100f;
        
        //Start working
        final SwingWorker work1 = new SwingWorker<String, Integer>(){
            protected String doInBackground(){
                try{
                    WorkScript();
                }catch(Exception e){
                    sendEvent("error", 
                            "<html>There was an error,<br/>check debug info.</html>");
                    System.err.println (e.getLocalizedMessage());
                    e.printStackTrace();
                }
                return null;
            }
            protected void done(){
                finished=true;
            }
        };
        work1.execute();
    }
    
    
    public void WorkScript() throws Exception{
        sendEvent("reset", String.valueOf(increment));
        for(String apk: apks){
        sendEvent("message", "Deleting files...");
            clean(apk);
            
            sendEvent("add", "Copying required files...");
            File smali = new File("framework"+sep+"smali.jar");
            File baksmali = new File("framework"+sep+"baksmali.jar");
            if(!smali.exists() || !baksmali.exists()){
                fileCopyFromJar("/resources/smali.jar", smali.getPath());
                fileCopyFromJar("/resources/baksmali.jar", baksmali.getPath());
                
            }
            if(ext.equals("apk")){
                //Copy apks\SystemUI.odex to framework\SystemUI.odex
                fileCopy("app"+sep+ apk+".odex", "framework"+sep+ apk+".odex");
            }
            
            sendEvent("add", "Disassembling...");
            execute("framework", Arrays.asList("java", "-Xmx512m","-jar", "baksmali.jar","-x", apk + ".odex",  "-a" + api));
            sendEvent("add", "Assembling...");
            execute("framework", Arrays.asList("java", "-Xmx512m","-jar", "smali.jar","out"+sep, "-o", "classes.dex"));
        
            sendEvent("add", "Creating directories...");
            directoryMake("deodexed");
            directoryMake("buildDEODEX");
            
            sendEvent("add", "Copying file...");
            //Copy from ..\framework\classes.dex to buildDEODEX\classes.dex
            fileCopy("framework"+sep+"classes.dex", "buildDEODEX"+sep+"classes.dex");
            
            sendEvent("add", "Unzipping file...");
            if(ext.equals("apk")){
                //Unzip ..\apks\SystemUI.apk to buildDEODEX\
                fileUnzip("app"+sep + apk + "." + ext, "buildDEODEX");
            }
            else{
                //Unzip ..\framework\SystemUI.apk to buildDEODEX\
                fileUnzip("framework"+sep + apk + "." + ext, "buildDEODEX");
            }
            
            sendEvent("add", "Zipping directory...");
            //Zip all buildDEODEX\ to ..\deodexed\Dincrement.apk
            directoryZip("deodexed"+sep+apk + "." + ext, "buildDEODEX", compression);
            
            //Delete all temp files
            sendEvent("add", "Deleting files...");
            clean(apk);
            echo(apk + " deodexed.");
        }
        //Open file explorer on the finished product
        sendEvent("done", "<html>Done:<br/>All files deodexed.</html>");
        directoryOpen("deodexed");
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
        fileDelete(CWD+"framework"+sep+"out"+sep);
        fileDelete(CWD+"framework"+sep+"classes.dex");
        fileDelete(CWD+"buildDEODEX"+sep);
        if(ext.equals("apk")){
            fileDelete(CWD+"buildDEODEX"+sep+apk+".odex");
            fileDelete(CWD+"framework"+sep+apk+".odex");
        }
    }
    
    
    
}

