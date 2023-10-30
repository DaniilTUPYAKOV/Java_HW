package markup;

import java.util.List;

public class Strikeout extends AbstractHighlighter {

    public Strikeout(List<?extends AbstractElement> list) {
        super(list, "~", "[s]", "[/s]");
    }
}
