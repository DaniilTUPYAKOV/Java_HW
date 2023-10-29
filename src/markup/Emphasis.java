package markup;

import java.util.List;

public class Emphasis extends AbstractElement {

    public Emphasis(List<MarkdownElement> list) {
        super(list);
    }

    public void toMarkDown(StringBuilder stringBuilder) {
        super.toMarkdownAbstract(stringBuilder, "*");
    }
    
}
