import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;

public class WordStatCountMiddleL1 {

    public static int[] mergeArray(int[] array, int[] arrayIndA, int[] arrayIndB) {

        int[] arrayIndResult = new int[arrayIndA.length + arrayIndB.length];
        int positionA = 0;
        int positionB = 0;
        for (int i = 0; i < arrayIndResult.length; i++) {
            if (positionB == arrayIndB.length) {
                arrayIndResult[i] = arrayIndA[positionA];
                positionA++;
            } else if (positionA == arrayIndA.length) {
                arrayIndResult[i] = arrayIndB[positionB];
                positionB++;
            } else if (array[arrayIndA[positionA]] <= array[arrayIndB[positionB]]) {
                arrayIndResult[i] = arrayIndA[positionA];
                positionA++;
            } else {
                arrayIndResult[i] = arrayIndB[positionB];
                positionB++;
            }
        }
        return arrayIndResult;
    }

    public static int[] sortArray(int[] array, int[] arrayInd) {

        if (arrayInd == null) {
            return null;
        }
        if (arrayInd.length < 2) {
            return arrayInd;
        }
        int[] arrayIndLeft = new int[arrayInd.length / 2];
        System.arraycopy(arrayInd, 0, arrayIndLeft, 0, arrayInd.length / 2);

        int[] arrayIndRight = new int[arrayInd.length - arrayInd.length / 2];
        System.arraycopy(arrayInd, arrayInd.length / 2, arrayIndRight, 0, arrayInd.length - arrayInd.length / 2);

        arrayIndLeft = sortArray(array, arrayIndLeft);
        arrayIndRight = sortArray(array, arrayIndRight);

        return mergeArray(array, arrayIndLeft, arrayIndRight);
    }

    public static boolean isWordPart(char c) {
        return Character.getType(c) == Character.DASH_PUNCTUATION | (Character.isLetter(c) | c == '\'');
    }

    public static int[] toInt(Integer[] array) {
        int[] converted = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            converted[i] = array[i].intValue();
        }
        return converted;
    }

    public static void main(String[] args) {

        if (args.length > 0) {

            int numberOfUnicwords = 0;
            LinkedHashMap<String, Integer> words = new LinkedHashMap<String, Integer>();

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        new FileInputStream(args[0]),
                        "UTF-8"));
                char[] buffer = new char[1024];
                try {
                    int reads = in.read(buffer);
                    StringBuilder wordHolder = new StringBuilder();

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
                                continue;
                            }
                            int k = 0;
                            while (i + k < reads && !isWordPart(buffer[i + k])) {
                                k++;
                            }
                            if (k != 0) {
                                String word = wordHolder.toString();
                                if (word.length() >= 5) {
                                    word = word.substring(2, word.length() - 2); // cut middle
                                    // добавляем слово в мапу
                                    int amount = 1;
                                    if (words.get(word) == null) {
                                        numberOfUnicwords += 1;
                                    } else {
                                        amount = amount + words.get(word);
                                    }
                                    words.put(word, amount);
                                }
                                i += k;
                                wordHolder.delete(0, wordHolder.toString().length());
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

            String[] wordsList = words.keySet().toArray(new String[numberOfUnicwords]);
            int[] appearance = toInt(words.values().toArray(new Integer[numberOfUnicwords]));

            int[] sortedIndexes = new int[appearance.length];
            int[] indexes = new int[appearance.length];
            for (int i = 0; i < appearance.length; i++) {
                indexes[i] = i;
            }
            sortedIndexes = sortArray(appearance, indexes);

            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        "UTF-8"));
                try {
                    for (int k = 0; k < numberOfUnicwords - 1; k++) {
                        out.write(
                                wordsList[sortedIndexes[k]] + " " + Integer.toString(appearance[sortedIndexes[k]]) + "\n");
                    }
                    if (numberOfUnicwords > 0) {
                        out.write(wordsList[sortedIndexes[numberOfUnicwords - 1]] + " "
                                + Integer.toString(appearance[sortedIndexes[numberOfUnicwords - 1]]));
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
