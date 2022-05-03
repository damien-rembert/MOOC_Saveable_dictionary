package dictionary;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class SaveableDictionary {

    private HashMap<String, String> dico;
    private HashMap<String, String> dicoReverse;
    private String path;

    public SaveableDictionary() {
        this.dico = new HashMap<>();
        this.dicoReverse = new HashMap<>();
    }


    public boolean load() {
        try (Scanner reader = new Scanner(Paths.get(this.path))) {
            while (reader.hasNextLine()) {
                String[] entry = reader.nextLine().split(":");
                this.add(entry[0], entry[1]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean save() {
        try (PrintWriter writer = new PrintWriter(this.path)) {
            this.dico.forEach((key1, value1) -> {writer.println(key1 + ":" + value1);});
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public SaveableDictionary(String file) {
        this();
        this.path = file;
    }
    
    public void add(String word, String translation) {
        if (this.dico.containsKey(word) || this.dicoReverse.containsKey(word)) {
            return;
            }
        this.dico.put(word, translation);
        this.dicoReverse.put(translation, word);
    }

    public String translate(String query) {
        if (this.dico.containsKey(query)) {
            return this.dico.get(query);
        } else if (this.dicoReverse.containsKey(query)) {
            return this.dicoReverse.get(query);
        } else {
            return null;
        }
    }

    public void delete(String query) {
        if (this.dico.containsKey(query)) {
            String delMe = this.dico.get(query);
            this.dico.remove(query);
            this.dicoReverse.remove(delMe);
        } else if (this.dicoReverse.containsKey(query)) {
            String delMe = this.dicoReverse.get(query);
            this.dicoReverse.remove(query);
            this.dico.remove(delMe);
        }
    }
    
}
