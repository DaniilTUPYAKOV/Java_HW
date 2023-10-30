package markup;

import java.util.List;

public class Strikeout extends HighlightElement {

    public Strikeout(List<MarkdownElement> list) {
        super(list, "~", "[s]");
    }
}
