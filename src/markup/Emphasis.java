package markup;

import java.util.List;

public class Emphasis extends HighlightElement {

    public Emphasis(List<MarkdownElement> list) {
        super(list, "*", "[i]", "[/i]");
    }
}
