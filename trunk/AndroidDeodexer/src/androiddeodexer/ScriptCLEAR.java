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
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 *
 * @author Luis Peregrina
 */
public class ScriptCLEAR extends SysUtils{
    private ProgressBar progress;
    float one = (1f/(1f))*100f;
    private String dir;
    boolean finished;
    
    public ScriptCLEAR(String dir, JFrame parent){
        this.dir = dir+sep;
        progress = new ProgressBar(parent);
        progress.setIconImage(new ImageIcon(getClass().getClassLoader()
                .getResource("resources/icons/main.png")).getImage());
        progress.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        
        
        //Start working
        final SwingWorker works = new SwingWorker<String, Integer>(){
            protected String doInBackground(){
                try{
                    work();
                    
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
                progress.Add(one, "Cleared files from folder.");
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
                    works.cancel(true);
                    System.out.println("Cancelled clearing.");
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
    
    private void work() throws IOException{
        progress.Add(0, "Deleting files...");
        fileDelete(dir);
    }
    
    
}
