package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList extends ParagraphAndList {

    protected AbstractList(List<ListItem> list, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(bbCodeOpenMarker, bbCodeCloseMarker);
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
    }
}
