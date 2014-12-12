package manPageHelp;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;

/**
 * A set of static helper classes for TreeItems.
 *
 * @author William Hartman
 */
public class TreeUtils {
    /**
     * Build a tree (for the structured help system) from the output of a XMLManPageReader.
     *
     * @param manPageList The list of XMLManPages, organized into ManPageCategories.
     * @return A tree made from HelpItemWrappers, composed of data from the XMLManPages.
     */
    public static TreeItem<HelpItemWrapper> buildManPageTree(ArrayList<ManPageCategory> manPageList) {
        TreeItem<HelpItemWrapper> root = new TreeItem<HelpItemWrapper>();

        for(ManPageCategory m: manPageList) {
            TreeItem<HelpItemWrapper> category = new TreeItem<HelpItemWrapper>(new HelpItemWrapper(m.getCategory(), m.getDescription()));

            for(XMLManPage x: m.getManPages()) {
                TreeItem<HelpItemWrapper> command = new TreeItem<HelpItemWrapper>(new HelpItemWrapper(x.getName() + " - General", x.getDescription()));
                command.getChildren().add(new TreeItem<HelpItemWrapper>(new HelpItemWrapper(x.getName() + " - Uses/Examples", x.getExamples())));
                command.getChildren().add(new TreeItem<HelpItemWrapper>(new HelpItemWrapper(x.getName() + " - Options", x.getOptions())));

                category.getChildren().add(command);
            }

            root.getChildren().add(category);
        }

        return root;
    }

    /**
     * Build a new TreeItem containing only the children of the passed TreeItem whose toString() contains the
     * passed filter String, ignoring case.
     *
     * The result TreeItem has a dummy root. The children are all at the same level.
     *
     *                     EXAMPLE:
     *                    FILTER: "a"
     *       INPUT TREE              RESULTING TREE
     *
     *          AA        __|\            (null)
     *         /  \      |    \    _________|_________
     *        AB  CB     |___ /    |     |     |     |
     *       /\    /\       |/    AA    AB     CA    DA
     *     BB CA  DA DD
     *
     *
     * @param toFilter The tree to filter
     * @param filter The string that the tree will be filtered with
     * @return The filtered tree
     */
    public static <T> TreeItem<T> filterFromToString(TreeItem<T> toFilter, String filter) {
        TreeItem<T> toReturn = new TreeItem<T>();
        filterFromToString(toFilter, filter, toReturn);
        return toReturn;
    }

    private static <T> void filterFromToString(TreeItem<T> toFilter, String filter, TreeItem<T> resultTree) {
        if(toFilter.getValue() != null && toFilter.getValue().toString().toLowerCase().contains(filter.toLowerCase())) {
            resultTree.getChildren().add(new TreeItem<T>(toFilter.getValue()));
        }

        if(toFilter.isLeaf()) {
            return;
        } else {
            for(TreeItem<T> t: toFilter.getChildren()) {
                filterFromToString(t, filter, resultTree);
            }
        }
    }

    /**
     * Search for a specific item in a Tree. Compares the output of getValue().toString() to the
     * passed targetString.
     *
     * @param toSearch The Tree to search in.
     * @param targetString The string that is being searched for. The returned item's toString should match this.
     * @return The matching TreeItem, or null if none match.
     */
    public static <T> TreeItem<T> search(TreeItem<T> toSearch, String targetString) {
        TreeItemWrapper<T> toReturn = new TreeItemWrapper<T>();
        search(toSearch, targetString, toReturn);
        return toReturn.t;
    }

    public static <T> void search(TreeItem<T> toSearch, String targetString, TreeItemWrapper<T> toReturn) {
        if(toSearch.getValue() != null && toSearch.getValue().toString().equals(targetString)) {
            toReturn.t = toSearch;
            return;
        }
        for(TreeItem<T> t: toSearch.getChildren()) {
            search(t, targetString, toReturn);
        }
    }

    private static class TreeItemWrapper<T> {
        public TreeItem<T> t;
    }
}
