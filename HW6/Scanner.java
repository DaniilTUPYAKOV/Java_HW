import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Scanner {

    // Reader features
    private Reader inReader;

    // buffer settings
    private char[] buffer = new char[1024];
    private int currentBufferPosition = -1;
    private int currentBufferReads = 0;
    private String systemLineSeparator = System.lineSeparator();
    private char[] lineSeparator = systemLineSeparator.toCharArray();

    // counters
    private char[] lastToken = null;
    private char[] lastWordToken = null;
    private char[] lastIntToken = null;
    private String lastLine = null;
    private int currentLastLinePosition = 0;

    // constructors
    public Scanner(InputStream input) {
        inReader = new BufferedReader(new InputStreamReader(input));
    }

    public Scanner(InputStream input, String charsetName) {
        try {
            inReader = new BufferedReader(new InputStreamReader(input, charsetName));
        } catch (IOException e) {
            System.out.println("Wrong charset name");
        }
    }

    public Scanner(String str) {
        inReader = new StringReader(str);
    }

    // closer
    public void close() {
        try {
            inReader.close();
        } catch (IOException e) {
            System.out.println("Failed to close reader");
        }
    }

    // useful private detection methods
    private static boolean isWordPart(char c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }

    private static boolean isWord(char[] token) {
        for (char c : token) {
            if (!isWordPart(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isIntPart(char c) {
        return Character.isDigit(c)
                || ((int) Character.toLowerCase(c) >= (int) 'a' && (int) Character.toLowerCase(c) <= (int) 'j')
                || (int) c == (int) '-' || Character.toLowerCase(c) == 'x';
    }

    private static boolean isInt(char[] token) {
        for (char c : token) {
            if (!isIntPart(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTokenPart(char c) {
        return isWordPart(c) || isIntPart(c);
    }

    private boolean isLineSeparator(char c) {
        if (lineSeparator.length == 1) {
            return c == lineSeparator[0];
        } else {
            return c == '\n';
        }
    }

    private String convertAbcToDec(char[] token) {
        for (int i = 0; i < token.length; i++) {
            if (token[i] != '-') {
                token[i] = (char) ((int) token[i] - 49);
            }
        }
        return String.valueOf(token).toLowerCase();
    }

    // Scanner methods
    public char[] readNext(String tokenType) {

        boolean needWord = true;
        boolean needInt = true;

        if (tokenType.equals("int")) {
            needWord = false;
        } else if (tokenType.equals("word")) {
            needInt = false;
        }

        StringBuilder tokenBuilder = new StringBuilder();
        boolean tokenDetected = false;

        if (lastLine != null) {
            for (int i = currentLastLinePosition; i < lastLine.length(); i++) {

                if ((isIntPart(lastLine.charAt(i)) && needInt) || (isWordPart(lastLine.charAt(i)) && needWord)) {
                    tokenBuilder.append(lastLine.charAt(i));

                } else {
                    int j = 0;
                    while (i + j < lastLine.length() && !isTokenPart(lastLine.charAt(i + j))) {
                        j++;
                    }
                    if (!tokenBuilder.isEmpty()) {
                        currentLastLinePosition = i + j;
                        tokenDetected = true;
                        break;
                    }
                }
            }
            if (currentLastLinePosition == lastLine.length()-1) {
                lastLine = null;
            }
            if (tokenDetected) {
                char[] token = new char[tokenBuilder.length()];
                tokenBuilder.getChars(0, tokenBuilder.length(), token, 0);
                return token;
            }
        }
        try {
            if (currentBufferPosition == -1) {
                currentBufferReads = inReader.read(buffer);
                currentBufferPosition = 0;
            }

            while (currentBufferReads >= 0) { // начинаем обработку буфера

                for (int i = currentBufferPosition; i < currentBufferReads; i++) { // идем по элементам буфера

                    if ((isIntPart(buffer[i]) && needInt) || (isWordPart(buffer[i]) && needWord)) {
                        tokenBuilder.append(buffer[i]);

                    } else {
                        int j = 0;
                        while (i + j < currentBufferReads && !isTokenPart(buffer[i + j])) {
                            j++;
                        }
                        if (!tokenBuilder.isEmpty()) {
                            currentBufferPosition = i + j;
                            tokenDetected = true;
                            break;
                        }
                    }
                }
                if (!tokenDetected) {
                    currentBufferReads = inReader.read(buffer);
                    currentBufferPosition = 0;
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read the input stream");
        }
        if (tokenDetected || (!tokenBuilder.isEmpty() && (currentBufferReads < 0))) {
            char[] token = new char[tokenBuilder.length()];
            tokenBuilder.getChars(0, tokenBuilder.length(), token, 0);
            return token;
        } else {
            return null;
        }
    }

    public String next() {
        if (lastToken != null) {
            char[] tempToken = lastToken;
            lastToken = null;
            return String.valueOf(tempToken);
        } else if (lastIntToken != null) {
            char[] tempToken = lastIntToken;
            lastIntToken = null;
            return String.valueOf(tempToken);
        } else if (lastWordToken != null) {
            char[] tempToken = lastWordToken;
            lastWordToken = null;
            return String.valueOf(tempToken);
        }
        return String.valueOf(this.readNext("all"));
    }

    public boolean hasNext() {
        if (lastToken == null && lastIntToken == null && lastWordToken == null) {
            lastToken = this.readNext("all");
            if (lastToken == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public String nextLine() {
        if (lastLine != null) {
            String tempLine = lastLine;
            lastLine = null;
            return tempLine;
        } else {
            try {
                if (currentBufferPosition == -1) {
                    currentBufferReads = inReader.read(buffer);
                    currentBufferPosition = 0;
                }

                StringBuilder lineBuilder = new StringBuilder();
                boolean lineDetected = false;

                while (currentBufferReads >= 0) { // начинаем обработку буфера

                    for (int i = currentBufferPosition; i < currentBufferReads; i++) { // идем по элементам буфера

                        if (!isLineSeparator(buffer[i])) {
                            lineBuilder.append(buffer[i]);
                        } else {
                            currentBufferPosition = i + 1;
                            lineDetected = true;
                            break;
                        }
                    }
                    if (!lineDetected) {
                        currentBufferReads = inReader.read(buffer);
                        currentBufferPosition = 0;
                    } else {
                        break;
                    }
                }
                if (lineBuilder.isEmpty()) {
                    return null;
                } else {
                    return lineBuilder.toString();
                }
            } catch (IOException e) {
                System.out.println("Failed to read the input stream");
                return null;
            }
        }
    }

    public boolean hasNextLine() {
        if (lastLine == null) {
            lastLine = this.nextLine();
            currentLastLinePosition = 0;
        }
        if (lastLine != null) {
            return true;
        } else {
            return false;
        }

    }

    // special methods
    public boolean hasNextInt() {
        if (lastIntToken == null) {
            if (lastToken == null) {
                lastIntToken = this.readNext("int");
            } else if (isInt(lastToken)){
                lastIntToken = lastToken;
                lastToken = null;
            }
        }
        if (lastIntToken != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasNextWord() {
        if (lastWordToken == null) {
            if (lastToken == null) {
                lastWordToken = this.readNext("word");
            } else if (isWord(lastToken)) {
                lastWordToken = lastToken;
                lastToken = null;
            }
        }
        if (lastWordToken != null) {
            return true;
        } else {
            return false;
        }
    }

    public int nextInt() {
        if (lastIntToken == null) {
            if (!this.hasNextInt()) {
                return 0;
            };
        }
        char[] token = lastIntToken;
        lastIntToken = null;
        if (token.length > 2 && Character.toLowerCase(token[1]) == 'x') {
            return Integer.parseUnsignedInt(String.valueOf(token, 2, token.length-2), 16);
        } else if (Character.isDigit(token[token.length-1])) {
            return Integer.parseInt(String.valueOf(token));
        } else {
            return Integer.parseInt(convertAbcToDec(token));
        }
    }

    public String nextWord() {
        char[] token = null;
        if (lastWordToken == null) {
            if (!this.hasNextWord()) {
                return null;
            }
        } 
        token = lastWordToken;
        lastWordToken = null;
        return String.valueOf(token);
    }
}
