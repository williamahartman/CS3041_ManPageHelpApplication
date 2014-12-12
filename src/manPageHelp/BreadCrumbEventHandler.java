package manPageHelp;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;

/**
 * A simple EventHandler. Holds a reference to an TreeItem.
 *
 * @author William Hartman
 */
public abstract class BreadCrumbEventHandler implements EventHandler {
    TreeItem<HelpItemWrapper> helpPage;

    /**
     * Constructor
     *
     * @param helpPage The reference to the TreeItem that will be held.
     */
    public BreadCrumbEventHandler(TreeItem<HelpItemWrapper> helpPage) {
        this.helpPage = helpPage;
    }
}
