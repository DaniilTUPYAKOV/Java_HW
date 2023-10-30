package markup;

import java.util.List;

public abstract class AbstractElement implements MarkdownElement {

    private List<MarkdownElement> inside;

    public AbstractElement(List<MarkdownElement> list) {
        inside = list;
    }

    protected void mark(StringBuilder stringBuilder, String openMarker, String closeMarker, MarkType type) {

        stringBuilder.append(openMarker);
        if (type.equals(MarkType.MARKDOWN)) {
            for (MarkdownElement i : inside) {
                i.toMarkdown(stringBuilder);
            }
        }
        if (type.equals(MarkType.BBCODE)) {
            for (MarkdownElement i : inside) {
                i.toBBCode(stringBuilder);
            }
        }
        stringBuilder.append(closeMarker);
    }
}
