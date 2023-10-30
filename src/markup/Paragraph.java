package markup;

import java.util.List;

public class Paragraph extends AbstractElement {

    public Paragraph(List<MarkdownElement> list) {
        super(list);
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.mark(stringBuilder, "", "", MarkType.MARKDOWN);
    }

    public void toBBCode(StringBuilder stringBuilder) {
        super.mark(stringBuilder, "", "", MarkType.BBCODE);
    }
    
}
