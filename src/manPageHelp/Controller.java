package manPageHelp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * This class handles the UI behavior.
 *
 * @author William Hartman
 */
public class Controller implements Initializable{
    @FXML
    private TextField searchBox;
    @FXML
    private TreeView<HelpItemWrapper> manPageList;
    @FXML
    private ToolBar breadCrumbBar;
    @FXML
    private Label manPageTextBox;
    private TreeItem<HelpItemWrapper> fullManPageList;
    private TreeItem<HelpItemWrapper> currentScreen;


    @Override
    /**
     * This method is called by the FXMLLoader when initialization is complete
     * Initialize UI elements. Also loads in Man pages from XML and builds a Tree.
     */
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert searchBox != null : "fx:id=\"searchBox\" was not injected: check your FXML file 'simple.fxml'.";
        assert manPageList != null : "fx:id=\"manPageList\" was not injected: check your FXML file 'simple.fxml'.";
        assert breadCrumbBar != null : "fx:id=\"breadCrumbBar\" was not injected: check your FXML file 'simple.fxml'.";
        assert manPageTextBox != null : "fx:id=\"manPageTextBox\" was not injected: check your FXML file 'simple.fxml'.";

        fullManPageList = TreeUtils.buildManPageTree(XMLManPageReader.readXMLManPageFile("manpages.xml"));
        manPageTextBox.setWrapText(true);

        for(TreeItem<HelpItemWrapper> t: fullManPageList.getChildren()) {
            t.setExpanded(true);
        }
        manPageList.setRoot(fullManPageList);

        addEventFilters();

        showScreen(fullManPageList.getChildren().get(0));
    }

    /**
     * Convenience method to add event filters to the TreeView with the man pages and the search box.
     */
    private void addEventFilters() {
        searchBox.addEventFilter(KeyEvent.KEY_RELEASED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        String toSearch = searchBox.getCharacters().toString();

                        if(toSearch.equals("")) {
                            manPageList.setRoot(fullManPageList);
                            showScreen(currentScreen);
                            manPageList.getSelectionModel().select(currentScreen);
                        } else {
                            manPageList.setRoot(TreeUtils.filterFromToString(fullManPageList, toSearch));
                            showScreen(currentScreen);
                        }
                    }
                });

        manPageList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<HelpItemWrapper>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<HelpItemWrapper>> observable, TreeItem<HelpItemWrapper> oldValue,
                                TreeItem<HelpItemWrapper> newValue) {
                showScreen(newValue);
            }
        });
    }

    /**
     * Show the passed TreeItem.
     * Update the text shown in the main pane, as well as the breadcrumbs on the top bar
     * so that they reflect the passed item.
     *
     * @param toShow The HelpItemWrapper that will be shown.
     */
    private void showScreen(TreeItem<HelpItemWrapper> toShow) {
        if (toShow != null) {
            //Update the text pane.
            manPageTextBox.setText(toShow.getValue().getArticle());
            currentScreen = toShow;

            //Deal with breadcrumbs
            breadCrumbBar.getItems().retainAll();

            //Find out if the Tree this HelpItemWrapper came from is a filtered tree from a search
            TreeItem<HelpItemWrapper> temp = toShow;
            while (temp.getParent() != null) {
                temp = temp.getParent();
            }
            TreeItem<HelpItemWrapper> rootOfToShowTree = temp;

            //if the HelpItemWrapper is from the full tree, use it. Otherwise, use its equivalent in the full tree.
            TreeItem<HelpItemWrapper> current;
            if (rootOfToShowTree.equals(fullManPageList)) {
                current = toShow;
            } else {
                current = TreeUtils.search(fullManPageList, toShow.getValue().toString());
                currentScreen = current;
            }

            //Build and place the breadcrumb buttons in the top bar.
            while (current != null) {
                Button bcButton = BreadCrumbButton.breadCrumbBuilder(current.getValue().toString());

                addBreadCrumbButtonEventFilter(bcButton, current);

                breadCrumbBar.getItems().add(0, bcButton);
                if (current.getParent() != null && current.getParent().getParent() != null) {
                    current = current.getParent();
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Convenience method to add event filters to a bread crumb button.
     *
     * @param b The button that will be added.
     * @param c The TreeItem that holds the current text in the text pane.
     */
    private void addBreadCrumbButtonEventFilter(Button b, TreeItem<HelpItemWrapper> c) {
        b.addEventFilter(MouseEvent.MOUSE_RELEASED,
                new BreadCrumbEventHandler(c) {
                    @Override
                    public void handle(Event event) {
                        searchBox.clear();
                        manPageList.setRoot(fullManPageList);
                        manPageList.getSelectionModel().select(helpPage);
                    }
                });
    }
}
