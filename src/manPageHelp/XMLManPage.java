package manPageHelp;

/**
 * A simple wrapper class for a Man Page that is read in from XML.
 * Consists of getters and setters for each piece of data.
 *
 * @author William Hartman
 */
public class XMLManPage {
    private String name;
    private String description;
    private String examples;
    private String options;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
