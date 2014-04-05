package androiddeodexer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.Timer;
import javax.swing.SwingWorker;

/**
 *
 * @author Luis Peregrina
 */
public class ScriptPULL extends Script{
    private String from;
    SwingWorker works;
    Timer timer;
    boolean finished = false;
    boolean waiting_for_device = true;
    int SCRIPT_TIMEOUT_LIMIT = 30;  //Seconds allowed for adb wait-for-device
    
    public ScriptPULL(String from){
        this.from = from;
        //Make window
        
        //Set OS dependent commands
        OSCommands();
        
        
        
        
        //Start working
        works = new SwingWorker<String, Integer>(){
            protected String doInBackground(){
                try{
                    Work();
                }catch(Exception e){
                    sendEvent("error", ""
                            + "<html>There was an error,<br/>check debug info.</html>");
                    System.err.println(e.getLocalizedMessage());
                    e.printStackTrace();
                }
                return null;
            }
            protected void done(){
                if(finished){
                    sendEvent("done", "Done pulling files.");
                }
            }
        };
        works.execute();
        
        //TIMER
        timer = new Timer(SCRIPT_TIMEOUT_LIMIT*1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(waiting_for_device){
                    System.out.println("Terminated the script because the time limit was reached.\n"
                        + "If you are running GNU/Linux try opening the firewall for 'lo' interface\n"
                        + "like 'sudo iptables -I INPUT -i lo -j ACCEPT' and try again.");
                    sendEvent("error",
                            "<html>There was an timeout,<br/>check debug info.</html>");
                    killServer();   //Kill the server
                }

            }
        });
        timer.start();
        

    }
    
    

    public void Work() throws Exception
    {
        sendEvent("reset", String.valueOf(1f/3f));
        sendEvent("message", "Waiting for device...");
        execute("", Arrays.asList(CWD+"resources"+sep+adb, "wait-for-device"));
        waiting_for_device = false;
        sendEvent("add", "Making folder...");
        directoryMake(from);
        sendEvent("add", "Copying files...");
        execute("", Arrays.asList(CWD+"resources"+sep+adb,"pull", "/system/" + from + "/", from+sep) );
        finished=true;
    }
    
    //To execute inside a worker, because they have a method execute().
    public void killServer()
    {
        try{
            works.cancel(true);
            execute("", Arrays.asList(CWD+"resources"+sep+adb,"kill-server")); 
            echo("ADB Daemon closed.");
            timer.stop();
        }catch(Exception e){
            System.err.println("Unable to close ADB, it was probably never started.");
            e.printStackTrace();
        }
    }
}
