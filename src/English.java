import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class English {
    private HashSet<String> words;

    public English() {
        words = new HashSet<>();
        loadWordsFromFile("dictionary.txt");
    }

    private void loadWordsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error loading words from file: " + e.getMessage());
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toLowerCase());
    }

    // Getter method for the words field
    public HashSet<String> getWords() {
        return words;
    }
}
