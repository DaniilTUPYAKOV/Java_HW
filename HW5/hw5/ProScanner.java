package hw5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

public class ProScanner {
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
    private boolean lastTokenIsWord = true;
    private boolean lastTokenIsInt = true;
    private String lastLine = null;
    private int currentLastLinePosition = 0;

    // special cached tokens
    private ArrayList<String> chachedWords = new ArrayList<String>();
    private ArrayList<String> chachedInts = new ArrayList<String>();

    // constructors
    public ProScanner(InputStream input) {
        inReader = new BufferedReader(new InputStreamReader(input));
    }

    public ProScanner(String str) {
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

    private static boolean isNumberPart(char c) {
        return Character.isDigit(c)
                || ((int) Character.toLowerCase(c) >= (int) 'a' && (int) Character.toLowerCase(c) <= (int) 'f')
                || (int) c == (int) '-';
    }

    private static boolean isTokenPart(char c) {
        return isWordPart(c) || isNumberPart(c);
    }

    private boolean isLineSeparator(char c) {
        if (lineSeparator.length == 1) {
            return c == lineSeparator[0];
        } else {
            return c == '\n';
        }
    }

    // Scanner methods
    public String next() {
        if (lastToken != null) {
            String tempToken = lastToken;
            lastToken = null;
            lastTokenIsInt = true;
            lastTokenIsWord = true;
            return tempToken;
        } else {
            StringBuilder tokenBuilder = new StringBuilder();
            boolean tokenDetected = false;
            boolean tempTokenTypeWord;
            boolean tempTokenTypeInt;

            if (lastLine != null) {
                for (int i = currentLastLinePosition; i < lastLine.length(); i++) {

                    tempTokenTypeInt = isNumberPart(lastLine.charAt(i));
                    tempTokenTypeWord = isWordPart(lastLine.charAt(i));

                    if (tempTokenTypeInt || tempTokenTypeWord) {
                        tokenBuilder.append(lastLine.charAt(i));
                        if (!tempTokenTypeInt) {
                            lastTokenIsInt = false;
                        }
                        if (!tempTokenTypeWord) {
                            lastTokenIsWord = false;
                        }
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

                        tempTokenTypeInt = isNumberPart(buffer[i]);
                        tempTokenTypeWord = isWordPart(buffer[i]);

                        if (tempTokenTypeInt || tempTokenTypeWord) {
                            tokenBuilder.append(buffer[i]);
                            if (!tempTokenTypeInt) {
                                lastTokenIsInt = false;
                            }
                            if (!tempTokenTypeWord) {
                                lastTokenIsWord = false;
                            }
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
                if (tokenDetected || (!tokenBuilder.isEmpty() && (currentBufferReads < 0))) {
                    return tokenBuilder.toString();
                } else {
                    return null;
                }
            } catch (IOException e) {
                System.out.println("Failed to read the input stream");
                return null;
            }
        }
    }

    public boolean isLastInt() {
        return lastTokenIsInt;
    }

    public boolean hasNext() {
        if (lastToken == null) {
            lastToken = this.next();
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
    public boolean hasNext(String type) {
        if (lastToken == null) {
            lastToken = this.next();
        }
        if (type.equals("int")) {

            if (!chachedInts.isEmpty()) {
                return true;
            }
            if (lastTokenIsInt) {
                return true;
            } else {
                while (!lastTokenIsInt && lastToken != null) {
                    chachedWords.add(lastToken);
                    lastToken = null;
                    lastTokenIsInt =true;
                    lastTokenIsWord = true;
                    lastToken = this.next();
                }
                return lastTokenIsInt;
            }
        }
        if (type.equals("word")) {

            if (!chachedWords.isEmpty()) {
                return true;
            }

            if (lastTokenIsWord) {
                return true;
            } else {
                while (!lastTokenIsWord && lastToken != null) {
                    chachedInts.add(lastToken);
                    lastToken = null;
                    lastTokenIsInt =true;
                    lastTokenIsWord = true;
                    lastToken = this.next();
                }
                return lastTokenIsWord;
            }
        }
        return false;
    }

    public int nextInt(int radix) {
        String token = null;
        if (!chachedInts.isEmpty()) {
            token = chachedInts.get(0);
            chachedInts.remove(0);
        } else {
            if (lastTokenIsInt && lastToken != null) {
                token = lastToken;
            } else {
                if (hasNext("int")) {
                    token = lastToken;
                }
            }
            lastToken = null;
            lastTokenIsInt = true;
            lastTokenIsWord = true;
        }
        if (token == null) {
            return 0;
        }
        if (radix > 10) {
            return Integer.parseUnsignedInt(token, 16);
        } else {
            return Integer.parseInt(token, radix);
        }

    }

    public String nextWord() {
        String token = null;
        if (!chachedWords.isEmpty()) {
            token = chachedWords.get(0);
            chachedWords.remove(0);
        } else {
            if (lastTokenIsWord && lastToken != null) {
                token = lastToken;
            } else {
                if (hasNext("word")) {
                    token = lastToken;
                }
            }
            lastToken = null;
            lastTokenIsInt = true;
            lastTokenIsWord = true;
        }
        return token;
    }
}
