import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class WordDataBase {
    // will contain key -> category, value -> words
    private HashMap<String, String[]> ENGwordList;
    private HashMap<String, String[]> PLwordList;

    // used to pick random categories
    private ArrayList<String> ENGcategories;
    private ArrayList<String> PLcategories;

    public WordDataBase() {
        try {
            ENGwordList = new HashMap<>();
            ENGcategories = new ArrayList<>();
            PLwordList = new HashMap<>();
            PLcategories = new ArrayList<>();
            int lineCounter = 0;

            // get file path
            String filePath = getClass().getClassLoader().getResource(CommonConstants.DATA_PATH).getPath();
            if (filePath.contains("%20")) filePath = filePath.replaceAll("%20", " ");
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // iterates through each line in the data.txt
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineCounter < 3) {
                // splits the data.txt by ","
                String[] parts = line.split(",");

                // the first word of each line = category
                String category = parts[0];
                ENGcategories.add(category);

                // the rest of the values from parts will be the words relative to the category
                String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                ENGwordList.put(category, values);
                } else {
                    String[] parts = line.split(",");

                    // the first word of each line = category
                    String category = parts[0];
                    PLcategories.add(category);

                    // the rest of the values from parts will be the words relative to the category
                    String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                    PLwordList.put(category, values);

                }
                lineCounter++;
            }
        } catch (IOException e) {
            System.out.println("Error in data.txt file: " + e);
        }
    }

    public String[] loadChallange() {
        Random rand = new Random();

        if (Hangman.language == "English") {
            // generate random number to choose category
            String category = ENGcategories.get(rand.nextInt(ENGcategories.size()));

            // generate random number to choose the value from category
            String[] categoryValues = ENGwordList.get(category);
            String word = categoryValues[rand.nextInt(categoryValues.length)];

            // [0] -> category and [1] -> word
            return new String[]{category.toUpperCase(), word.toUpperCase()};
        } else if (Hangman.language == "Polish") {
            // generate random number to choose category
            String category = PLcategories.get(rand.nextInt(PLcategories.size()));

            // generate random number to choose the value from category
            String[] categoryValues = PLwordList.get(category);
            String word = categoryValues[rand.nextInt(categoryValues.length)];

            // [0] -> category and [1] -> word
            return new String[]{category.toUpperCase(), word.toUpperCase()};
        }
        return null;
    }

}
