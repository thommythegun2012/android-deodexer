package androiddeodexer;

import java.util.EventListener;

/**
 *
 * @author Luis Peregrina
 */
public interface ProgressBarListener extends EventListener {
    public void somethingHappened(ProgressBarEvent pve);
}
