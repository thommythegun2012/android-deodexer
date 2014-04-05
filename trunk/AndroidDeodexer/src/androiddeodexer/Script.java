package androiddeodexer;

import java.util.ArrayList;

/**
 *
 * @author Luis Peregrina
 */
public class Script extends SysUtils {
    private transient ArrayList<ProgressBarListener> listeners;
    public SysUtils u = new SysUtils();
    
    /**
     *
     * @param l
     */
    synchronized public void addProgressBarListener(ProgressBarListener l) {
    if (listeners == null){
        listeners = new ArrayList();
    }
    listeners.add(l);
  }  

  /** Remove a listener for ProgressBarEvents
     * @param l */
  synchronized public void removeProgressBarListener(ProgressBarListener l) {
    if (listeners == null){
        listeners = new ArrayList();
    }
    listeners.remove(l);
  }
  

  /** HERE IS THE METHOD TO UPDATE/CHANGE THE PROGRESSBAR!
     * @param action
     * @param message 
  */
  public void sendEvent(String action, String message) {
    // if we have no listeners, do nothing...
    if (listeners != null && !listeners.isEmpty()) {
      // create the event object to send
      ProgressBarEvent event = new ProgressBarEvent(this, action, message);

      for(ProgressBarListener target: listeners){
          target.somethingHappened(event);
      }
    }
  }
}
