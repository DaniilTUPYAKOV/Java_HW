package markup;

import java.util.List;

public class Paragraph extends AbstractElement {

    public Paragraph(List<MarkdownElement> list) {
        super(list);
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdownAbstract(stringBuilder, "");
    }
    
}
