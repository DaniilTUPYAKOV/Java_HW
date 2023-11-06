// package structClasses;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractElement implements MarkableElement {

    protected List<MarkableElement> inside;
    protected StringBuilder text;
    private TagType type;

    protected AbstractElement(List<?extends AbstractElement> list, TagType type) {
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        if (list == null) {
            return;
        }
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
        this.type = type;
    }

    public void toHtml(StringBuilder stringBuilder) {

        Service.addTag(stringBuilder, type, false, 0);
        for (MarkableElement i : inside) {
            i.toHtml(stringBuilder);
        }
        Service.addTag(stringBuilder, type, true, 0);
    }
}
