package markup;

import java.util.List;

public class Strong extends HighlightElement {

    public Strong(List<MarkdownElement> list) {
        super(list, "__", "[b]");
    }
}
