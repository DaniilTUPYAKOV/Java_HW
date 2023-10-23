package hw5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Scanner0 {

    // Reader features
    private Reader inReader;

    // buffer settings
    private char[] buffer = new char[1024];
    private int currentBufferPosition = -1;
    private int currentBufferReads = 0;
    private String systemLineSeparator = System.lineSeparator();
    private char[] lineSeparator = systemLineSeparator.toCharArray();

    // counters
    private String lastToken = null;
    private String lastWordToken = null;
    private String lastIntToken = null;
    private String lastLine = null;
    private int currentLastLinePosition = 0;

    // constructors
    public Scanner0(InputStream input) {
        inReader = new BufferedReader(new InputStreamReader(input));
    }

    public Scanner0(InputStream input, String charsetName) {
        try{
            inReader = new BufferedReader(new InputStreamReader(input, charsetName));
        } catch (IOException e) {
            System.out.println("Wrong charset name");
        }
    }

    public Scanner0(String str) {
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

    private static boolean isWord(String token) {
        for (char c : token.toCharArray()){
            if (!isWordPart(c)){
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

    private static boolean isInt(String token) {
        for (char c : token.toCharArray()){
            if (!isIntPart(c)){
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

    private String convertAbcToDec(String token) {
        char[] digits = token.toCharArray();
        for (int i=0; i< digits.length; i++){
            if (digits[i] != '-'){
                digits[i] = (char) ((int) digits[i] - 49);
            }
        }
        return String.valueOf(digits).toLowerCase();
    }

    // Scanner methods
    public String next(String target) {

        boolean needWord = true;
        boolean needInt = true;

        if (target.equals("int")) {
            needWord = false;
            if (lastIntToken != null) {
                String tempToken = lastIntToken;
                lastIntToken = null;
                return tempToken;
            }
        } else if (target.equals("word")) {
            needInt = false;
            if (lastWordToken != null) {
                String tempToken = lastWordToken;
                lastWordToken = null;
                return tempToken;
            }
        } else if (target.equals("token")) {
            if (lastToken != null) {
                String tempToken = lastToken;
                lastToken = null;
                return tempToken;
            }
        }

        StringBuilder tokenBuilder = new StringBuilder();
        boolean tokenDetected = false;
        boolean tempTokenTypeWord = true;
        boolean tempTokenTypeInt = true;

        if (lastLine != null) {
            for (int i = currentLastLinePosition; i < lastLine.length(); i++) {

                tempTokenTypeInt = isIntPart(lastLine.charAt(i)) && needInt;
                tempTokenTypeWord = isWordPart(lastLine.charAt(i)) && needWord;

                if (tempTokenTypeInt || tempTokenTypeWord) {
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
            if (tokenDetected) {
                return tokenBuilder.toString();
            }
        }
        try {
            if (currentBufferPosition == -1) {
                currentBufferReads = inReader.read(buffer);
                currentBufferPosition = 0;
            }

            while (currentBufferReads >= 0) { // начинаем обработку буфера

                for (int i = currentBufferPosition; i < currentBufferReads; i++) { // идем по элементам буфера

                    tempTokenTypeInt = isIntPart(buffer[i]) && needInt;
                    tempTokenTypeWord = isWordPart(buffer[i]) && needWord;

                    if (tempTokenTypeInt || tempTokenTypeWord) {
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
            return tokenBuilder.toString();
        } else {
            return null;
        }
    }

    public String next() {
        return this.next("token");
    }

    public boolean hasNext() {
        if (lastToken == null) {
            lastToken = this.next("token");
        }
        if (lastToken != null) {
            return true;
        } else {
            return false;
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
            if (lastToken != null && isInt(lastToken)){
                lastIntToken = lastToken;
                lastToken = null;
            } else {
                lastIntToken = this.next("int");
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
            if (lastToken != null && isWord(lastToken)){
                lastWordToken = lastToken;
                lastToken = null;
            } else {
                lastWordToken = this.next("word");
            }
        }
        if (lastWordToken != null) {
            return true;
        } else {
            return false;
        }
    }

    public int nextInt(int radix) {
        String token = null;
        if (lastIntToken != null) {
            token = lastIntToken;
        } else {
            if (this.hasNextInt()) {
                token = lastIntToken;
            }
        }
        lastIntToken = null;
        if (token == null) {
            return 0;
        }
        if (token.length() > 2 && token.toLowerCase().charAt(1) == 'x') {
            // return Integer.parseUnsignedInt(token.substring(2, token.length()), 16);
            return Integer.parseUnsignedInt(token.substring(2, token.length()), 16);
        } else if (Character.isDigit(token.charAt(token.length()-1))) {
            return Integer.parseInt(token);
        } else {
            return Integer.parseInt(convertAbcToDec(token), radix);
        }
    }

    public String nextWord() {
        String token = null;
        if (lastWordToken != null) {
            token = lastWordToken;
        } else {
            if (this.hasNextWord()) {
                token = lastWordToken;
            }
        }
        lastWordToken = null;
        return token;
    }
}
