package md2html;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Parser {
    private StringBuilder parsedText;
    private BufferedReader reader;

    public Parser(String in, StringBuilder out) throws IOException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8));
        parsedText = out;
    }

    public void Close() throws IOException {
        reader.close();
    }

    private class Marker {
        private TagType type;
        private String marker;
        private boolean closer;
        private int index;

        public Marker(TagType type, String marker, int index, boolean closer) {
            this.marker = marker;
            this.type = type;
            this.index = index;
            this.closer = closer;
        }

        public int getIndex() {
            return index;
        }

        public boolean isSameMarker(String markerToCheck) {
            return (marker.equals(markerToCheck));
        }

        public int markerLength() {
            return marker.length();
        }

        public void addTag(StringBuilder stringBuilder) {
            Service.addTag(stringBuilder, type, closer, 0);
        }
    }

    private List<Marker> findMarkerPairs(StringBuilder stringBuilder) {
        final Set<Character> markerChars = Set.of(
                '*', '_', '`', '-');

        final Map<String, TagType> markers = Map.of(
                "*", TagType.EMPHASISE,
                "_", TagType.EMPHASISE,
                "**", TagType.STRONG,
                "__", TagType.STRONG,
                "--", TagType.STRIKEOUT,
                "`", TagType.CODE);

        final Map<Character, String> specialSymbols = Map.of(
                '<', "&lt;", '>', "&gt;", '\\', "", '&', "&amp;");

        List<Marker> readyMarkers = new ArrayList<>();
        Stack<Marker> openedMarkers = new Stack<>();
        int markerEnd;
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (markerChars.contains(stringBuilder.charAt(i)) && (i - 1 < 0 || stringBuilder.charAt(i - 1) != '\\')) {
                markerEnd = i;
                if (i + 1 < stringBuilder.length() && markerChars.contains(stringBuilder.charAt(i + 1))) {
                    markerEnd++;
                }
                String marker = stringBuilder.substring(i, markerEnd + 1);
                if (!openedMarkers.empty() && openedMarkers.peek().isSameMarker(marker)) {
                    Marker readyForPair = openedMarkers.pop();
                    readyMarkers.add(readyForPair);
                    readyMarkers.add(new Marker(markers.get(marker), marker, i, true));
                } else {
                    openedMarkers.add(new Marker(markers.get(marker), marker, i, false));
                }
                i = markerEnd;
            }
            if (specialSymbols.containsKey(stringBuilder.charAt(i))) {
                String newSymbol = specialSymbols.get(stringBuilder.charAt(i));
                stringBuilder.deleteCharAt(i);
                stringBuilder.insert(i, newSymbol);
                i += newSymbol.length() - 1;
            }
        }
        readyMarkers.sort(new Comparator<Marker>() {
            public int compare(Marker marker1, Marker marker2) {
                return marker1.getIndex() - marker2.getIndex();
            }
        });
        return readyMarkers;
    }

    private void parseBlok(StringBuilder stringBuilder, TagType type, int headerOrder) {
        Service.addTag(parsedText, type, false, headerOrder);
        List<Marker> markers = findMarkerPairs(stringBuilder);
        int start = 0;
        for (Marker marker : markers) {
            parsedText.append(stringBuilder.subSequence(start, marker.getIndex()));
            marker.addTag(parsedText);
            start = marker.getIndex() + marker.markerLength();
        }
        parsedText.append(stringBuilder.subSequence(start, stringBuilder.length()));
        Service.addTag(parsedText, type, true, headerOrder);
    }

    public void Parse() throws IOException {
        StringBuilder blokBuilder = new StringBuilder();
        boolean finish = false;
        while (!finish) {
            String line = reader.readLine();

            if (line == null) {
                finish = true;
            } else if (line.length() > 0) {
                if (!blokBuilder.isEmpty()) {
                    blokBuilder.append(System.lineSeparator());
                }
                blokBuilder.append(line);
            }

            if ((finish || line.length() == 0) && !blokBuilder.isEmpty()) {
                int i = 0;
                while (blokBuilder.charAt(i) == '#') {
                    i++;
                }
                if (i != 0 && blokBuilder.charAt(i) == ' ') { /* Header detected */
                    blokBuilder.delete(0, i + 1);
                    parseBlok(blokBuilder, TagType.HEADER, i);
                } else { /* Paragraph detected */
                    parseBlok(blokBuilder, TagType.PARAGRAPH, 0);
                }
                blokBuilder.setLength(0);
                parsedText.append(System.lineSeparator());
            }
        }
    }
}
