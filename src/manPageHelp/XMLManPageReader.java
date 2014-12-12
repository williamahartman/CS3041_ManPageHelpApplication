package manPageHelp;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class has one static method that reads from an XML file
 *
 * @author William Hartman
 */
public class XMLManPageReader {

    //Names of XML elements
    public static final String CATEGORY = "category";
    public static final String CATEGORYDESCRIPTION = "categorydescription";
    public static final String COMMAND = "command";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String EXAMPLES = "examples";
    public static final String OPTIONS = "options";

    /**
     * Reads an XML file, returning a list of objects that wrap the data
     *
     * @param filePath The relative path to the XML file
     * @return The list of wrapper objects produced from the XML file
     */
    public static ArrayList<ManPageCategory> readXMLManPageFile(String filePath) {
        ArrayList<ManPageCategory> toReturn = new ArrayList<ManPageCategory>();

        try {
            //Setup
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(filePath);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            ManPageCategory currentManPageCategory = null;
            XMLManPage currentManPage = null;

            //Parsing the XML file
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                //Read start elements, either building a wrapper object or setting its data.
                if(event.isStartElement()) {
                    String elementName = event.asStartElement().getName().getLocalPart();

                    if(elementName.equals(CATEGORY)) {
                        currentManPageCategory = new ManPageCategory();
                        Iterator<Attribute> attributes = event.asStartElement().getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(NAME)) {
                                currentManPageCategory.setCategory(attribute.getValue());
                            }
                        }
                    }

                    if(elementName.equals(CATEGORYDESCRIPTION) && currentManPageCategory != null) {
                        event = eventReader.nextEvent();
                        currentManPageCategory.setDescription(event.toString());
                        continue;
                    }

                    if(elementName.equals(COMMAND)) {
                        currentManPage = new XMLManPage();

                        Iterator<Attribute> attributes = event.asStartElement().getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(NAME)) {
                                currentManPage.setName(attribute.getValue());
                            }
                        }
                    }

                    if(elementName.equals(DESCRIPTION) && currentManPage != null) {
                        event = eventReader.nextEvent();
                        currentManPage.setDescription(event.toString());
                        continue;
                    }

                    if(elementName.equals(EXAMPLES) && currentManPage != null) {
                        event = eventReader.nextEvent();
                        currentManPage.setExamples(event.asCharacters().getData());
                        continue;
                    }

                    if(elementName.equals(OPTIONS) && currentManPage != null) {
                        event = eventReader.nextEvent();
                        currentManPage.setOptions(event.asCharacters().getData());
                        continue;
                    }
                }

                //Ignores all elements except the main one. Adds the finished object to the list.
                if(event.isEndElement()) {
                    String elementName = event.asEndElement().getName().getLocalPart();

                    if(elementName.equals(COMMAND) && currentManPage != null && currentManPageCategory != null) {
                        currentManPageCategory.addToManPages(currentManPage);
                    }

                    if(elementName.equals(CATEGORY) && currentManPageCategory != null) {
                        toReturn.add(currentManPageCategory);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    /**
     * Test the XML reader.
     *
     * @param filePath The file that will be read.
     */
    public static void test(String filePath) {
        ArrayList<ManPageCategory> receivedItems = readXMLManPageFile(filePath);
        System.out.println("READING " + filePath + "\n");

        System.out.println("FOUND " + receivedItems.size() + " CATEGORIES\n");

        for(ManPageCategory m: receivedItems) {
            System.out.println(m.getCategory());
            for(XMLManPage x: m.getManPages()) {
                System.out.println();
                System.out.println(x.getName());
                System.out.println(x.getDescription());
                System.out.println(x.getExamples());
                System.out.println(x.getOptions());
                System.out.println();
            }
        }
    }
}
