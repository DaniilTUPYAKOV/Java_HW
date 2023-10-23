import java.util.Arrays;
import hw5.Scanner;

public class Reverse {
    public static void main(String[] args) {
        // Считывание неполной матрицы
        Scanner fieldScanner = new Scanner(System.in); // открываем сканер по строкам матрицы
        int[][] fieldArray = new int[1024][0];
        int fillNumberLine = 0;
        while (fieldScanner.hasNextLine()) { // цикл обработки строк матрицы
            Scanner lineScanner = new Scanner(fieldScanner.nextLine()); // сканер по строке матрицы
            int[] buffer = new int[1024];
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
            buffer = Arrays.copyOf(buffer, fillnumberColumn);
            fieldArray[fillNumberLine] = buffer;
            lineScanner.close();
            fillNumberLine += 1;
        }

        fieldScanner.close();
        // Вывод строк
        StringBuilder lineHolder = new StringBuilder();
        for (int i = 0; i < fillNumberLine; i++) {
            int lengthLine = fieldArray[fillNumberLine - 1 - i].length;
            for (int j = 0; j < lengthLine; j++) {
                lineHolder.append(Integer.toString(fieldArray[fillNumberLine - 1 - i][lengthLine - 1 - j]));
                if (j != lengthLine - 1) {
                    lineHolder.append(" ");
                }
            }
            System.out.println(lineHolder.toString());
            lineHolder.setLength(0);
        }
    }
}
