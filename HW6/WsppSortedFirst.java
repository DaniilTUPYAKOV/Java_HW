import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WsppSortedFirst {

    public static boolean isLineSeparator(char c) {
        String systemLineSeparator = System.lineSeparator();
        char[] lineSeparator = systemLineSeparator.toCharArray();
        if (lineSeparator.length == 1) {
            return c == lineSeparator[0];
        } else {
            return c == '\n';
        }
    }

    static class IntList {
        private int[] intArray;
        private int size;

        public IntList() {
            this.intArray = new int[1024];
        }

        public boolean add(int element) {
            if (size == intArray.length) {
                intArray = Arrays.copyOf(intArray, intArray.length * 2);
            }
            intArray[size] = element;
            size++;
            return true;
        }

        public boolean set(int index, int element) {
            if (index < 0 || index >= size) {
                return false;
            }
            intArray[index] = element;
            return true;
        }

        public int get(int index) {
            return intArray[index];
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < size; i++) {
                builder.append(Integer.toString(intArray[i]));
                if (i < size - 1) {
                    builder.append(" ");
                }
            }
            return builder.toString();
        }

        public int size() {
            return size;
        }
    }

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
        Map<String, IntList> words = new HashMap<String, IntList>();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]),
                    "UTF-8"));
            try {

                char[] buffer = new char[1024];
                int reads = in.read(buffer);
                StringBuilder wordHolder = new StringBuilder();
                int numberOfWords = 0;
                HashSet<String> wordInLine = new HashSet<String>();
                boolean newLine = false;

                while (reads >= 0 | wordHolder.toString().length() != 0) { // начинаем обработку буфера
                    int i = 0;
                    while (i < reads) { // идем по элементам буфера
                        int j = 0;
                        while (i + j < reads && isWordPart(buffer[i + j])) {
                            j++;
                        }
                        if (j != 0) {
                            wordHolder.append((new String(buffer, i, j)).toLowerCase());
                            i += j;
                        }
                        int k = 0;
                        while (i + k < reads && !isWordPart(buffer[i + k])) {
                            if (isLineSeparator(buffer[i + k])) {
                                newLine = true;
                            }
                            k++;
                        }
                        if (k != 0 && !wordHolder.isEmpty()) {
                            numberOfWords += 1;
                            String word = wordHolder.toString();
                            int amount = 1;
                            IntList wordInfo = words.get(word);
                            if (wordInfo == null) {
                                numberOfUnicwords += 1;
                                wordInfo = new IntList();
                                wordInfo.add(amount);
                            } else {
                                amount += wordInfo.get(0);
                                wordInfo.set(0, amount);
                            }
                            if (!wordInLine.contains(word)) {
                                wordInfo.add(numberOfWords);
                            }
                            wordInLine.add(word);
                            words.put(word, wordInfo);
                            wordHolder.setLength(0);
                        }
                        i += k;
                        if (newLine) {
                            wordInLine = new HashSet<String>();
                            newLine = false;
                        }
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

        List<Map.Entry<String, IntList>> entries = new ArrayList<Map.Entry<String, IntList>>(words.entrySet());

        entries.sort(new Comparator<Map.Entry<String, IntList>>() {
            public int compare(Entry<String, IntList> entry1, Entry<String, IntList> entry2) {
                return entry1.getKey().compareTo(entry2.getKey());
            }
        });

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF-8"));
            try {
                for (int k = 0; k < numberOfUnicwords; k++) {
                    out.write(entries.get(k).getKey());
                    out.write(" ");
                    out.write(entries.get(k).getValue().toString());
                    if (k < numberOfUnicwords - 1) {
                        out.write("\n");
                    }
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
