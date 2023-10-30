package markup;

import java.util.List;

public class Strong extends AbstractHighlighter {

    public Strong(List<?extends AbstractElement> list) {
        super(list, "__", "[b]", "[/b]");
    }
}
