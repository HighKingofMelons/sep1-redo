package app.utils;

import app.models.Article;
import app.models.Book;
import app.models.CD;
import app.models.DVD;
import app.models.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Exporter {
    private static final String saveLocation = "website/exportedData.xml";

    /**
     * Loads saved data from a predefined path
     * @return Saved items
     */
    public static ArrayList<Item> loadSave() {
        ArrayList<Item> loaded = new ArrayList<>();
        Document contents = null;
        try {
            File file = new File(saveLocation);
            if (file.exists()) { // data has already been saved and can be loaded
                //https://stackoverflow.com/a/562207
                // initializing builder
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                // parsing the file
                try {
                    contents = builder.parse(file);
                } catch (SAXException exc) {
                    System.out.println("Saved file is corrupt! (File parsing)");
                    System.out.println("Save filepath: "+saveLocation);
                    System.out.println(exc.getMessage());
                    return loaded;
                }
            } else {
                System.out.println("Saved file not found!");
            }
        } catch (Exception e) {
            System.out.println("Error occoured while loading save! (Other file error)");
            System.out.println("Save filepath: "+saveLocation);
            System.out.println(e.getMessage());
            return loaded;
        }

        // converting saved file to items
        if (contents == null) // nothing has been saved
            return loaded;

        try {
            // looping over books
            NodeList bookElems = contents.getElementsByTagName("Book");
            for (int i=0; i< bookElems.getLength(); i++) {
                HashMap<String,Object> fields = getAllFields(bookElems.item(i));
                loaded.add(new Book(
                        (String) fields.get("BorrowerEmail"), (LocalDate) fields.get("ReturnDate"), (String) fields.get("Title"),
                        (ArrayList<String>) fields.get("Reservations"), (LocalDate) fields.get("DateAdded"),
                        (String) fields.get("Author"), (String) fields.get("ISBN"))
                );
            }

            // looping over articles
            NodeList articleElems = contents.getElementsByTagName("Article");
            for (int i=0; i< articleElems.getLength(); i++) {
                HashMap<String,Object> fields = getAllFields(articleElems.item(i));
                loaded.add(new Article(
                        (String) fields.get("BorrowerEmail"), (LocalDate)fields.get("ReturnDate"), (String) fields.get("Title"),
                        (ArrayList<String>) fields.get("Reservations"), (LocalDate)fields.get("DateAdded"),
                        (String) fields.get("Author"), (String) fields.get("Magazine"))
                );
            }

            // looping over articles
            NodeList cdElems = contents.getElementsByTagName("CD");
            for (int i=0; i< cdElems.getLength(); i++) {
                HashMap<String,Object> fields = getAllFields(cdElems.item(i));
                loaded.add(new CD(
                        (String) fields.get("BorrowerEmail"), (LocalDate) fields.get("ReturnDate"), (String) fields.get("Title"),
                        (ArrayList<String>) fields.get("Reservations"), (LocalDate) fields.get("DateAdded"))
                );
            }

            // looping over articles
            NodeList dvdElems = contents.getElementsByTagName("DVD");
            for (int i=0; i< dvdElems.getLength(); i++) {
                HashMap<String,Object> fields = getAllFields(dvdElems.item(i));
                loaded.add(new DVD(
                        (String) fields.get("BorrowerEmail"), (LocalDate) fields.get("ReturnDate"), (String) fields.get("Title"),
                        (ArrayList<String>) fields.get("Reservations"), (LocalDate) fields.get("DateAdded"))
                );
            }
        } catch (Exception e) {
            System.out.println("Saved file is corrupt! (XML Error)");
            System.out.println("Save filepath: "+saveLocation);
            System.out.println(e.getMessage());
            return loaded;
        }

        return loaded;
    }

    /**
     * Save provided items to predetermined save path
     * @param items The items to save
     */
    public static void saveItems(ArrayList<Item> items) { // n is items count
        // initializing document
        Document document; // 1 operation

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 2 operations
            DocumentBuilder builder = factory.newDocumentBuilder(); // 2 operations
            document = builder.newDocument(); // 2 operations
        } catch (Exception e) { // 1 operation
            System.out.println("Error occoured while saving items! (XML Doc builder)"); // 1 operation
            System.out.println("Save filepath: "+saveLocation); // 2 operations
            System.out.println(e.getMessage()); // 2 operations
            return; // 1 operation
            // Best case scenario O(13) = O(1) operations
        }
        Element root =  document.createElement("ItemsInfo"); // 2 operations
        document.appendChild(root); // 1 operation

        // adding items to root
        for (Item item: items) { // n operations
            // creating item element
            Element itemElem = null; // n operations
            switch (item.getType()) { // n operations
                case Book -> itemElem = document.createElement("Book"); // n? operations
                case Article -> itemElem = document.createElement("Article"); // n? operations
                case CD -> itemElem = document.createElement("CD"); // n? operations
                case DVD -> itemElem = document.createElement("DVD"); // n? operations
            }
            document.createElement("Item"); // n operations
            root.appendChild(itemElem); // n operations

            // appending title
            Element titleElem = document.createElement("Title"); // 2n operations
            titleElem.appendChild(document.createTextNode(item.getTitle())); // 3n operations
            itemElem.appendChild(titleElem); // n operations

            // appending date added to library
            Element dateAddedElem = document.createElement("DateAdded"); // 2n operations
            dateAddedElem.appendChild(document.createTextNode(item.getDateAddedToLibrary().toString())); // 4n operations
            itemElem.appendChild(dateAddedElem); // n operations

            // appending return date
            if (item.getReturnDate() != null) { // 2n operations
                Element returnDateElem = document.createElement("ReturnDate"); // 2n operations
                returnDateElem.appendChild(document.createTextNode(item.getReturnDate().toString())); // 4n operations
                itemElem.appendChild(returnDateElem); // n operations
            }

            // appending borrower email
            if (item.getBorrowerEmail() != null) { // 2n operations
                Element borrowerEmailElem = document.createElement("BorrowerEmail"); // 2n operations
                borrowerEmailElem.appendChild(document.createTextNode(item.getBorrowerEmail())); // 3n operations
                itemElem.appendChild(borrowerEmailElem); // n operations
            }

            // appending type specific fields
            switch(item.getType()) { // n operations
                case Book -> {
                    Book book = (Book) item; // 2n? operations
                    // appending author
                    Element authorElem = document.createElement("Author"); // 2n operations
                    authorElem.appendChild(document.createTextNode(book.getAuthor())); // 3n operations
                    itemElem.appendChild(authorElem); // n operations
                    // appending ISBN
                    Element ISBNElem = document.createElement("ISBN"); // 2n operations
                    ISBNElem.appendChild(document.createTextNode(book.getISBN())); // 3n operations
                    itemElem.appendChild(ISBNElem); // n operations
                }
                case Article -> {
                    Article article = (Article) item; // 2n operations
                    // appending author
                    Element authorElem = document.createElement("Author"); // 2n operations
                    authorElem.appendChild(document.createTextNode(article.getAuthor())); // 3n operations
                    itemElem.appendChild(authorElem); // n oeprations
                    // appending magazine
                    Element magazineElem = document.createElement("Magazine"); // 2n operations
                    magazineElem.appendChild(document.createTextNode(article.getMagazine())); // 3n operations
                    itemElem.appendChild(magazineElem); // n operations
                }
            }

            // appending reservations
            Element reservationsElem = document.createElement("Reservations"); // 2n operations
            for (String res: item.getReservations()) { // (m+1)n operations
                Element resElem = document.createElement("Reservation"); // (2m)n operations
                resElem.appendChild(document.createTextNode(res)); // (2m)n operations
                reservationsElem.appendChild(resElem); // mn operations
            }
            itemElem.appendChild(reservationsElem); // n operations
        }
        // footnote: loop takes m*n operations (m is item's reservation count, n is items count)

        // converting Document to string (java why)
        // https://stackoverflow.com/a/5456836
        String xmlString; // 1 operation
        try {
            TransformerFactory tf = TransformerFactory.newInstance(); // 2 operations
            Transformer transformer = tf.newTransformer(); // 2 operations
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // 1 operation
            StringWriter writer = new StringWriter(); // 2 operations
            transformer.transform(new DOMSource(document), new StreamResult(writer)); // 3 operations
            xmlString = writer.getBuffer().toString().replaceAll("\n|\r", ""); // 4 operations
        } catch (Exception e) { // 1 operation
            System.out.println("Error occoured while saving items! (XML to String)"); // 1 operation
            System.out.println("Save filepath: "+saveLocation); // 2 operations
            System.out.println(e.getMessage()); // 2 operations
            return; // 1 operation
            // here bigO is O(n*m)
        }


        try {
            // create file in case it doesnt exist
            File savedFile = new File(saveLocation); // 2 operations
            savedFile.createNewFile(); // creating the new file in case it doesnt exist, 1 operation

            // saving the encoded xml
            FileWriter writer = new FileWriter(saveLocation); // 2 operations
            writer.write(xmlString); // 1 operations
            writer.close(); // 1 operation
        } catch (Exception e) { // 1 operation
            System.out.println("Error occoured while saving items! (File writing)"); // 1 operation
            System.out.println("Save filepath: "+saveLocation); // 2 operations
            System.out.println(e.getMessage()); // 2 operations

            System.out.println("--- XML String to save ---"); // 1 operation
            System.out.println(xmlString); // 1 operation
        }

        // In Total:
        // Best case scenario: bigO(1)
        // Worst case scenario: bigO(n*m) = bigO(n)
        // Average case scenario: bigO(n*m) = bigO(n)
    }

    /**
     * Gets classes fields saved inside the given XML element node
     * @param itemNode The XML Node to which the fields were saved
     * @return Fields, each of which is mapped to their respective name
     */
    private static HashMap<String, Object> getAllFields(Node itemNode) {
        HashMap<String, Object> returnedValues = new HashMap<>();

        NodeList fields = itemNode.getChildNodes();
        for (int i=0; i< fields.getLength(); i++) {
            Node field = fields.item(i);

            switch (field.getNodeName()) {
                case "Title" -> returnedValues.put("Title",field.getTextContent());
                case "Author" -> returnedValues.put("Author",field.getTextContent());
                case "Magazine" -> returnedValues.put("Magazine",field.getTextContent());
                case "ISBN" -> returnedValues.put("ISBN",field.getTextContent());
                case "Reservations" -> {
                    ArrayList<String> savedReservations = new ArrayList<>();
                    NodeList resList = field.getChildNodes();
                    for (int resI=0; resI<resList.getLength(); resI++) {
                        Node res = resList.item(resI);
                        savedReservations.add(res.getTextContent());
                    }
                    returnedValues.put("Reservations", savedReservations);
                }
                case "BorrowerEmail" -> {
                    if (field.getTextContent() != null)
                        returnedValues.put("BorrowerEmail",field.getTextContent());
                    else
                        returnedValues.put("BorrowerEmail",null);
                }
                case "ReturnDate" -> {
                    if (field.getTextContent() != null)
                        returnedValues.put("ReturnDate",LocalDate.parse(field.getTextContent()));
                    else
                        returnedValues.put("ReturnDate",null);
                }
                case "DateAdded" -> {
                    if (field.getTextContent() != null)
                        returnedValues.put("DateAdded",LocalDate.parse(field.getTextContent()));
                    else
                        returnedValues.put("DateAdded",null);
                }
            }
        }

        return returnedValues;
    }
}
