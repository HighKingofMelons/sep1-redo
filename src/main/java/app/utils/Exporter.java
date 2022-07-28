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
    private static final String saveLocation = "exportedData.save";

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
    public static void saveItems(ArrayList<Item> items) {
        // initializing document
        Document document;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (Exception e) {
            System.out.println("Error occoured while saving items! (XML Doc builder)");
            System.out.println("Save filepath: "+saveLocation);
            System.out.println(e.getMessage());
            return;
        }
        Element root =  document.createElement("ItemsInfo");
        document.appendChild(root);

        // adding items to root
        for (Item item: items) {
            // creating item element
            Element itemElem = null;
            switch (item.getType()) {
                case Book -> itemElem = document.createElement("Book");
                case Article -> itemElem = document.createElement("Article");
                case CD -> itemElem = document.createElement("CD");
                case DVD -> itemElem = document.createElement("DVD");
            }
            document.createElement("Item");
            root.appendChild(itemElem);

            // appending title
            Element titleElem = document.createElement("Title");
            titleElem.appendChild(document.createTextNode(item.getTitle()));
            itemElem.appendChild(titleElem);

            // appending date added to library
            Element dateAddedElem = document.createElement("DateAdded");
            dateAddedElem.appendChild(document.createTextNode(item.getDateAddedToLibrary().toString()));
            itemElem.appendChild(dateAddedElem);

            // appending return date
            if (item.getReturnDate() != null) {
                LocalDate date = LocalDate.now();
                Element returnDateElem = document.createElement("ReturnDate");
                returnDateElem.appendChild(document.createTextNode(item.getReturnDate().toString()));
                itemElem.appendChild(returnDateElem);
            }

            // appending borrower email
            if (item.getBorrowerEmail() != null) {
                Element borrowerEmailElem = document.createElement("BorrowerEmail");
                borrowerEmailElem.appendChild(document.createTextNode(item.getBorrowerEmail()));
                itemElem.appendChild(borrowerEmailElem);
            }

            // appending type specific fields
            switch(item.getType()) {
                case Book -> {
                    Book book = (Book) item;
                    // appending author
                    Element authorElem = document.createElement("Author");
                    authorElem.appendChild(document.createTextNode(book.getAuthor()));
                    itemElem.appendChild(authorElem);
                    // appending ISBN
                    Element ISBNElem = document.createElement("ISBN");
                    ISBNElem.appendChild(document.createTextNode(book.getISBN()));
                    itemElem.appendChild(ISBNElem);
                }
                case Article -> {
                    Article article = (Article) item;
                    // appending author
                    Element authorElem = document.createElement("Author");
                    authorElem.appendChild(document.createTextNode(article.getAuthor()));
                    itemElem.appendChild(authorElem);
                    // appending magazine
                    Element magazineElem = document.createElement("Magazine");
                    magazineElem.appendChild(document.createTextNode(article.getMagazine()));
                    itemElem.appendChild(magazineElem);
                }
            }

            // appending reservations
            Element reservationsElem = document.createElement("Reservations");
            for (String res: item.getReservations()) {
                Element resElem = document.createElement("Reservation");
                resElem.appendChild(document.createTextNode(res));
                reservationsElem.appendChild(resElem);
            }
            itemElem.appendChild(reservationsElem);
        }

        // converting Document to string (java why)
        // https://stackoverflow.com/a/5456836
        String xmlString;
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            xmlString = writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (Exception e) {
            System.out.println("Error occoured while saving items! (XML to String)");
            System.out.println("Save filepath: "+saveLocation);
            System.out.println(e.getMessage());
            return;
        }


        try {
            // create file in case it doesnt exist
            File savedFile = new File(saveLocation);
            savedFile.createNewFile(); // creating the new file in case it doesnt exist

            // saving the encoded xml
            FileWriter writer = new FileWriter(saveLocation);
            writer.write(xmlString);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occoured while saving items! (File writing)");
            System.out.println("Save filepath: "+saveLocation);
            System.out.println(e.getMessage());

            System.out.println("--- XML String to save ---");
            System.out.println(xmlString);
        }
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
