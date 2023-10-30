package markup;

import java.util.List;

public class Emphasis extends AbstractHighlighter {

    public Emphasis(List<?extends AbstractElement> list) {
        super(list, "*", "[i]", "[/i]");
    }
}
