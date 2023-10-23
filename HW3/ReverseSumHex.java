import java.util.Arrays;
import java.util.Scanner;

public class ReverseSumHex {
    public static void main(String[] args) {
        // Считывание неполной матрицы
        Scanner fieldScanner = new Scanner(System.in); // открываем сканер по строкам матрицы
        int[][] fieldArray = new int[1000][0];
        int fillNumberLine = 0;
        while (fieldScanner.hasNextLine()) { // цикл обработки строк матрицы

            Scanner lineScanner = new Scanner(fieldScanner.nextLine()); // сканер по строке матрицы
            int[] buffer = new int[1000];
            int fillnumberColumn = 0;
            while (lineScanner.hasNext()) {
                if (fillnumberColumn == buffer.length) {
                    buffer = Arrays.copyOf(buffer, buffer.length *2);
                }
                buffer[fillnumberColumn] = Integer.parseUnsignedInt(lineScanner.next(), 16);
                fillnumberColumn += 1;
            }
            if (fillNumberLine == fieldArray.length) {
                fieldArray = Arrays.copyOf(fieldArray, fieldArray.length*2);
            }
            int[] lineArray = new int[fillnumberColumn];
            System.arraycopy(buffer, 0, lineArray, 0, fillnumberColumn);
            fieldArray[fillNumberLine] = lineArray;
            lineScanner.close();
            fillNumberLine += 1;
        }
        fieldScanner.close();
        // Вывод строк просуммированной матрицы
        for (int i = 0; i < fillNumberLine; i++) {
            if (fieldArray[i].length != 0) { // проверка на пустую строку оригинальной таблицы
                int j = 0;
                while (j < fieldArray[i].length) {
                    int n = 0;
                    int sum = 0;
                    while (n <= i) {
                        int m = 0;
                        while (m < fieldArray[n].length & m <= j) {
                            sum += fieldArray[n][m];
                            m++;
                        }
                        n++;
                    }
                    System.out.print(Integer.toUnsignedString(sum, 16));
                    if (j == fieldArray[i].length - 1) {
                        System.out.println("");
                    } else {
                        System.out.print(" ");
                    }
                    j++;
                }
            } else {
                System.out.println("");
            }
        }
    }
}
