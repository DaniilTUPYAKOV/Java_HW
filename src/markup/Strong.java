package markup;

import java.util.List;

public class Strong extends AbstractElement {

    public Strong(List<MarkdownElement> list) {
        super(list);
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdownAbstract(stringBuilder, "__");
    }
    
}
