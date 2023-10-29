package markup;

import java.util.List;

public class Strikeout extends AbstractElement {

    public Strikeout(List<MarkdownElement> list) {
        super(list);
    }

    public void toMarkDown(StringBuilder stringBuilder) {
        super.toMarkdownAbstract(stringBuilder, "~");
    }
    
}
