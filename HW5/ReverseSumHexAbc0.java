import java.util.Arrays;
// import java.util.Scanner;
import hw5.Scanner0;

public class ReverseSumHexAbc0 {

    public static String convertDecToAbc(String token) {
        char[] digits = token.toCharArray();
        for (int i=0; i< digits.length; i++){
            if (Character.isDigit(digits[i])) {
                digits[i] = (char)((int) digits[i] + 49);
            }
        }
        return String.valueOf(digits);
    }
    public static void main(String[] args) {
        // Считывание неполной матрицы
        Scanner0 fieldScanner = new Scanner0(System.in); // открываем сканер по строкам матрицы
        int[][] fieldArray = new int[1000][0];
        int fillNumberLine = 0;
        while (fieldScanner.hasNextLine()) { // цикл обработки строк матрицы
            Scanner0 lineScanner = new Scanner0(fieldScanner.nextLine()); // сканер по строке матрицы
            int[] buffer = new int[1024];
            int fillnumberColumn = 0;
            while (lineScanner.hasNextInt()) {
                if (fillnumberColumn == buffer.length) {
                    buffer = Arrays.copyOf(buffer, buffer.length * 2);
                }
                // buffer[fillnumberColumn] = Integer.parseUnsignedInt(lineScanner.next(), 10);
                buffer[fillnumberColumn] = lineScanner.nextInt(10);
                fillnumberColumn += 1;
            }
            if (fillNumberLine == fieldArray.length) {
                fieldArray = Arrays.copyOf(fieldArray, fieldArray.length * 2);
            }
            fieldArray[fillNumberLine] = Arrays.copyOf(buffer, fillnumberColumn);
            lineScanner.close();
            fillNumberLine += 1;
        }
        fieldScanner.close();

        for (int i = 0; i < fillNumberLine; i++) {
            for (int j = 0; j < fieldArray[i].length; j++) {
                int k = i - 1, m = j - 1;
                while (k >= 0 && j >= fieldArray[k].length) {
                    k--;
                }
                if (m >= 0) {
                    fieldArray[i][j] += fieldArray[i][m];
                }
                if (k >= 0) {
                    fieldArray[i][j] += fieldArray[k][j];
                    if (m >= 0) {
                        fieldArray[i][j] -= fieldArray[k][m];
                    }
                }
            }
        }
        


        for (int i = 0; i < fillNumberLine; i++) {
            System.out.println(convertDecToAbc(Arrays.toString(fieldArray[i]).replaceAll("\\[|]", "").replaceAll(", ", " ")));
        }
    }
}
