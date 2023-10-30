package markup;

import java.util.List;

public abstract class AbstractElement implements MarkableElement {

    protected List<MarkableElement> inside;
    protected String text;

    protected AbstractElement() {}

    protected void mark(StringBuilder stringBuilder, String openMarker, String closeMarker, MarkType type) {

        stringBuilder.append(openMarker);
        if (type.equals(MarkType.MARKDOWN)) {
            for (MarkableElement i : inside) {
                i.toMarkdown(stringBuilder);
            }
        }
        if (type.equals(MarkType.BBCODE)) {
            for (MarkableElement i : inside) {
                i.toBBCode(stringBuilder);
            }
        }
        stringBuilder.append(closeMarker);
    }
}
