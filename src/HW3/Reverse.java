import java.util.Arrays;
import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        // Считывание неполной матрицы
        Scanner fieldScanner = new Scanner(System.in); // открываем сканер по строкам матрицы
        int[][] fieldArray = new int[1024][0];
        int fillNumberLine = 0;
        int[] buffer = new int[1024];
        while (fieldScanner.hasNextLine()) { // цикл обработки строк матрицы
            Scanner lineScanner = new Scanner(fieldScanner.nextLine()); // сканер по строке матрицы
            int fillnumberColumn = 0;
            while (lineScanner.hasNext()) {
                if (fillnumberColumn == buffer.length) {
                    buffer = Arrays.copyOf(buffer, buffer.length * 2);
                }
                buffer[fillnumberColumn] = Integer.parseInt(lineScanner.next());
                fillnumberColumn += 1;
            }
            if (fillNumberLine == fieldArray.length) {
                fieldArray = Arrays.copyOf(fieldArray, fieldArray.length * 2);
            }
            fieldArray[fillNumberLine] = Arrays.copyOf(buffer, fillnumberColumn);;
            lineScanner.close();
            fillNumberLine += 1;
        }

        fieldScanner.close();
        // Вывод строк
        for (int i = 0; i < fillNumberLine; i++) {
            int lengthLine = fieldArray[fillNumberLine - 1 - i].length;
            for (int j = 0; j < lengthLine; j++) {
                System.out.print(fieldArray[fillNumberLine - 1 - i][lengthLine - 1 - j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}
