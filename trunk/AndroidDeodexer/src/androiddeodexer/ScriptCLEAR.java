package androiddeodexer;

import java.io.IOException;
import javax.swing.SwingWorker;

/**
 *
 * @author Luis Peregrina
 */
public class ScriptCLEAR extends Script{
    private String dir;
    boolean finished;
    
    public ScriptCLEAR(String dir){
        this.dir = dir+sep;
        
        
        
        //Start working
        final SwingWorker works = new SwingWorker<String, Integer>(){
            protected String doInBackground(){
                try{
                    work();
                    
                }catch(Exception e){
                    sendEvent("error_message","<html>There was an error,<br/>check debug info.</html>");
                    System.err.println(e.getLocalizedMessage());
                    e.printStackTrace();
                }
                return null;
            }
            protected void done(){
                sendEvent("done", "Cleared files from folder.");
                finished=true;
            }
        };
        works.execute();
        
    }
    
    private void work() throws IOException{
        sendEvent("reset", "1");
        sendEvent("message", "Deleting files...");
        fileDelete(CWD+dir);
    }
    
    
}
