public class SumLongSpace {
    public static void main(String[] args) {
        long result = 0;
        String fullString = String.join(" ", args);
        int currentSign = 1;
        int i = 0;
        while (i < fullString.length()) {
            int j = 0;
            while ((i + j < fullString.length()) && Character.isDigit(fullString.charAt(i + j))) {
                j++;
            }
            if (j != 0) {
                result += Long.valueOf(fullString.substring(i, i + j)) * currentSign;
                i += j;
                if (i >= fullString.length()){
                    break;
                }
                currentSign = 1;
            } 
            if (fullString.charAt(i) == '-') {
                currentSign *= -1;
            }
            i++;
        }
        System.out.println(result);
    }
}
