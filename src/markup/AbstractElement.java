package markup;

import java.util.List;

public abstract class AbstractElement implements MarkdownElement {

    private List<MarkdownElement> inside;

    public AbstractElement(List<MarkdownElement> list) {
        inside = list;
    }

    protected void toMarkdownAbstract(StringBuilder stringBuilder, String marker) {
        stringBuilder.append(marker);
        for (MarkdownElement i : inside) {
            i.toMarkdown(stringBuilder);
        }
        stringBuilder.append(marker);
    }
}
