import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordStatInput {

    public static boolean isWordPart(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION | (Character.isLetter(c) | c == '\'');
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println(
                "Not enough filenames. Expected: 'inputFilename outputFilename', have: '" 
                + Arrays.toString(args).replaceAll("\\[|]", "")
                + "'"
            );
            System.exit(0);
        }

        int numberOfUnicwords = 0;
        Map<String, Integer> words = new LinkedHashMap<String, Integer>();
        try {
            Scanner scaner = new Scanner(new FileInputStream(args[0]), "UTF-8");
            while (scaner.hasNextWord()) {
                String word = scaner.nextWord().toLowerCase();
                // добавляем слово в мапу
                int amount = 1;
                if (words.get(word) == null) {
                    numberOfUnicwords += 1;
                } else {
                    amount = amount + words.get(word);
                }
                words.put(word, amount);
            }
            scaner.close();
        } catch (IOException e) {
            System.out.println("Failed to find or open the file: '\'" + args[0] + "\'");
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF-8"));
            try {
                for (int k = 0; k < numberOfUnicwords - 1; k++) {
                    out.write(
                            entries.get(k).getKey() + " " + Integer.toString(entries.get(k).getValue()) + "\n");
                }
                if (numberOfUnicwords > 0) {
                    out.write(entries.get(numberOfUnicwords - 1).getKey() + " "
                            + Integer.toString(entries.get(numberOfUnicwords - 1).getValue()));
                }
            } catch (IOException e) {
                System.out.println("Failed to write data in created file: \'" + args[1] + "\'");
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Failed to create file: \'" + args[1] + "\'");
        }
    }
}