
public class tester {
    public static String convertAbcToDec(String token) {
        char[] digits = token.toCharArray();
        for (int i=0; i< digits.length; i++){
            digits[i] = (char) ((int) digits[i] - 49);
        }
        return String.valueOf(digits).toLowerCase();
    }
    public static void main(String[] args) {
        // Scanner fieldScanner = new Scanner("a b c hello");
        // // System.out.println(fieldScanner.hasNext());
        // // System.out.println(fieldScanner.nextWord());
        // // System.out.println(fieldScanner.hasNext());
        // System.out.println(fieldScanner.nextInt(10));
        // System.out.println(fieldScanner.nextInt(10));
        String token = "0xffffc";
        System.out.println(token.substring(2, token.length()));
    }
}
