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
        Scanner scanner = new Scanner(System.in); // открываем сканер по строкам матрицы
        int[] sumInColumn = new int[1000];
        int currentColumn = 0;
        int sum = 0;
        while (scanner.hasNextInt()) { // цикл обработки строк матрицы
            while (scanner.newLineBeforeNext()) {
                currentColumn = 0;
                sum = 0;
                System.out.print("\n");
            }
            if (currentColumn == sumInColumn.length) {
                sumInColumn = Arrays.copyOf(sumInColumn, currentColumn * 4);
            }
            sumInColumn[currentColumn] += scanner.nextInt();
            sum += sumInColumn[currentColumn];
            System.out.print(convertDecToAbc(Integer.toString(sum)) + " ");
            currentColumn++;
        }
        while (scanner.newLineBeforeNext()) {
                System.out.print("\n");
        }
        scanner.close();
    }
}