import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wspp {

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

    static class WordInfo {
        private int appearanceInText;
        private IntList metaData;

        public WordInfo(){
            appearanceInText = 1;
            metaData = new IntList();
        }

        public int getAppearance(){
            return appearanceInText;
        }

        public IntList getMetaData(){
            return metaData;
        }

        public void addAppearance(){
            appearanceInText++;
        }

        public void addMetaData(int data){
            metaData.add(data);
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
        Map<String, WordInfo> words = new LinkedHashMap<String, WordInfo>();
        
        try {
            int numberOfWords = 0;
            Scanner scanner = new Scanner(new FileInputStream(args[0]), "UTF-8");
            while (scanner.hasNextWord()) { 
                String word = scanner.nextWord().toLowerCase();
                // добавляем слово в мапу
                numberOfWords += 1;
                WordInfo wordInfo = words.get(word);
                if (wordInfo != null) {
                    wordInfo.addAppearance();
                } else {
                    wordInfo = new WordInfo();
                }
                wordInfo.addMetaData(numberOfWords);
                words.put(word, wordInfo);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Failed to find or open the file: '\'" + args[0] + "\'");
        }

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF-8"));
            try {
                for (Map.Entry<String, WordInfo> entry : words.entrySet()){
                    out.write(entry.getKey());
                    out.write(" ");
                    out.write(Integer.toString(entry.getValue().getAppearance()));
                    out.write(" ");
                    out.write(entry.getValue().getMetaData().toString());
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
    }
}
