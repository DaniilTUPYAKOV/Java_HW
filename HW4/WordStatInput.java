import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;


public class WordStatInput {

    public static boolean isWordPart(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION | (Character.isLetter(c) | c == '\'');
    }

    public static void main(String[] args) {

        if (args.length > 0) {

            Map<String, Integer> words = new LinkedHashMap<String, Integer>();

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        new FileInputStream(args[0]),
                        "UTF-8"));
                char[] buffer = new char[1024];
                try {
                    int reads = in.read(buffer);
                    StringBuilder wordHolder = new StringBuilder();

                    while (reads >= 0 || wordHolder.toString().length() != 0) { // начинаем обработку буфера
                        int i = 0;
                        while (i < reads) { // идем по элементам буфера
                            int j = 0;
                            while (i + j < reads && isWordPart(buffer[i + j])) {
                                j++;
                            }
                            if (j != 0) {
                                wordHolder.append((new String(buffer, i, j)).toLowerCase());
                                i += j;
                                continue;
                            }
                            int k = 0;
                            while (i + k < reads && !isWordPart(buffer[i + k])) {
                                k++;
                            }
                            if (k != 0 && !wordHolder.isEmpty()) {
                                String word = wordHolder.toString();
                                // добавляем слово в мапу
                                int amount = 1;
                                Integer appearance = words.get(word);
                                if (appearance != null) {
                                    amount += appearance.intValue();
                                }
                                words.put(word, amount);
                                wordHolder.setLength(0);
                            }
                            i += k;
                        }
                        reads = in.read(buffer);
                    }
                } catch (IOException e) {
                    System.out.println("Failed to read file: \'" + args[0] + "\'");
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to find or open the file: '\'" + args[0] + "\'");
            }

            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        "UTF-8"));
                try {
                    for (Map.Entry<String, Integer> entry : words.entrySet()){
                        out.write(entry.getKey());
                        out.write(" ");
                        out.write(Integer.toString(entry.getValue().intValue()));
                        out.write("\n");
                    }
                } catch (IOException e) {
                    System.out.println("Failed to write data in created file: \'" + args[1] + "\'");
                } finally {
                    out.close();
                }
            } catch (IOException e) {
                System.out.println("Failed to create file: \'" + args[1] + "\'");
            }
        } else {
            System.out.println("Not enough filenames");
        }

    }
}
