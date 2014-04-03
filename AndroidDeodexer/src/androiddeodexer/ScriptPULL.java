/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package androiddeodexer;

import gui.ProgressBar;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author Luis Peregrina
 */
public class ScriptPULL extends SysUtils{
    private ProgressBar progress;
    float one = (1f/(3f))*100f;
    private String from;
    boolean finished = false;
    
    public ScriptPULL(){
        //error
    }
    
    public ScriptPULL(String from, JFrame parent){
        this.from = from;
        //Make window
        progress = new ProgressBar(parent);
        progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                .getResource("resources/icons/main.png")).getImage());
        progress.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        progress.setModal(true);
        
        //Set OS dependent commands
        OSCommands();
        
        //Start working
        final SwingWorker works = new SwingWorker<String, Integer>(){
            protected String doInBackground(){
                try{
                    Work();
                }catch(Exception e){
                    progress.setTitle("Done.");
                    progress.message("<html>There was an error,<br/>check debug info.</html>");
                    progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                            .getResource("resources/icons/error.png")).getImage());
                    System.err.println(e.getLocalizedMessage());
                    e.printStackTrace();
                }
                return null;
            }
            protected void done(){
                progress.setTitle("Done.");
                progress.Add(one, "Done pulling files.");
                progress.toggleOK(true);
                progress.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                        .getResource("resources/icons/ok.png")).getImage());
                finished=true;

                
            }
        };
        works.execute();
        
        //Cancel if JDialog is closed.
        progress.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                if(!finished){
                    echo("Closing window");
                    works.cancel(true);
                    //Quit
                    killServer();
                    System.out.println("Cancelled pulling.");
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

    public void Work() throws Exception
    {
        progress.Add(0, "Waiting for device...");
        execute("", Arrays.asList(CWD+"resources"+sep+adb, "wait-for-device"));
        progress.Add(one, "Making folder...");
        directoryMake(from);
        progress.Add(one, "Copying files...");
        execute("", Arrays.asList(CWD+"resources"+sep+adb,"pull", "/system/" + from + "/", from+sep) );
    }
    
    //To execute inside a worker, because they have a method execute().
    private void killServer()
    {
        try{
            execute("", Arrays.asList(CWD+"resources"+sep+adb,"kill-server"));   
        }catch(Exception e){
            System.err.println("Unable to close ADB!");
            e.printStackTrace();
        }
    }
    
    
    
}
