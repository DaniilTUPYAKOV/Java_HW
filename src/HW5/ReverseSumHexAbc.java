
import java.util.Arrays;

public class ReverseSumHexAbc {

    public static String convertDecToAbc(String token) {
        char[] digits = token.toCharArray();
        for (int i = 0; i < digits.length; i++) {
            if (Character.isDigit(digits[i])) {
                digits[i] = (char) ((int) digits[i] + 49);
            }
        }
        return String.valueOf(digits);
    }

    public static void main(String[] args) {
        // Считывание неполной матрицы
        Scanner fieldScanner = new Scanner(System.in); // открываем сканер по строкам матрицы
        int[] sumInColumn = new int[1000];

        while (fieldScanner.hasNextLine()) { // цикл обработки строк матрицы
            Scanner lineScanner = new Scanner(fieldScanner.nextLine()); // сканер по строке матрицы
            int currentColumn = 0;
            int sum = 0;
            while (lineScanner.hasNext()) {
                if (currentColumn == sumInColumn.length) {
                    sumInColumn = Arrays.copyOf(sumInColumn, currentColumn * 4);
                }
                sumInColumn[currentColumn] += lineScanner.nextInt();
                sum += sumInColumn[currentColumn];
                System.out.print(convertDecToAbc(Integer.toString(sum)) + " ");
                currentColumn++;
            }
            lineScanner.close();
            System.out.print("\n");
        }
        fieldScanner.close();
    }
}