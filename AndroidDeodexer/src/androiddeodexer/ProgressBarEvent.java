package androiddeodexer;

import java.util.EventObject;

/**
 *
 * @author Luis Peregrina
 */
public class ProgressBarEvent extends EventObject {
    private String action;
    private String message;
    
    public ProgressBarEvent(Object source, String action, String message){
        super(source);
        this.action = action;
        this.message = message;
    }
    
    public String getAction(){
        return action;
    }
    public String getMessage(){
        return message;
    }
}
