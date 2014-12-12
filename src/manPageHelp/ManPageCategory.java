package manPageHelp;

import java.util.ArrayList;

/**
 * A Wrapper for a category of XML man pages.
 *
 * This class holds a list of XML pages, and is produced by the XML parser.
 *
 * @author William Hartman
 */
public class ManPageCategory {
    public String category;
    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<XMLManPage> manPages;

    public ManPageCategory() {
        manPages = new ArrayList<XMLManPage>();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void addToManPages(XMLManPage manPage) {
        manPages.add(manPage);
    }

    public ArrayList<XMLManPage> getManPages() {
        return manPages;
    }
}
