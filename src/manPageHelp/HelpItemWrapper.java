package manPageHelp;

/**
 * A Wrapper class for a help article. This class is designed to be held in a TreeItem.
 * It simply holds two strings, one for the title and one for the main text of the article.
 *
 * @author William Hartman
 */
public class HelpItemWrapper {
    private String name;
    private String article;

    public HelpItemWrapper(String name, String article) {
        this.name = name;
        this.article = article;
    }

    /**
     * Override toString. This is done because TreeView titles are based on the output of
     * this method.
     *
     * @return The name of the HelpItemWrapper
     */
    public String toString() {
        return name;
    }

    public String getArticle() {
        return  this.article;
    }
}
