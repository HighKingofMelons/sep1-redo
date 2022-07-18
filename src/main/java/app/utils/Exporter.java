package app.utils;

import app.models.Item;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Exporter {
    private static final String saveLocation = "";

    /**
     * Loads saved data from a predefined path
     * @return Saved items
     */
    public static ArrayList<Item> loadSave() {
        ArrayList<Item> loaded = new ArrayList<>();
        String contents = null;
        try {
            File file = new File(saveLocation);
            if (file.exists()) { // data has already been saved and can be loaded
                contents = "";
                Scanner fileReader = new Scanner(file);
                while (fileReader.hasNextLine()) {
                    contents += fileReader.nextLine();
                }
            } else {
                System.out.println("Saved file not found!");
            }
        } catch (Exception e) {
            System.out.println("Error occoured while loading save!");
            System.out.println("Save filepath: "+saveLocation);
            System.out.println(e.getMessage());
            return loaded;
        }

        if (contents != null) { // something has been saved
            XMLDecoder
        }

        return loaded;
    }
}
