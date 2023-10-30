package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractElement implements MarkableElement {

    protected List<MarkableElement> inside;
    protected String text;

    protected AbstractElement(List<?extends AbstractElement> list) {
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        if (list == null) {
            return;
        }
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
    }

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
