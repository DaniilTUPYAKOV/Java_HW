package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList extends ParagraphAndList {

    public AbstractList(List<ListItem> list, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(bbCodeOpenMarker, bbCodeCloseMarker);
        List<MarkdownElement> listNew = new ArrayList<MarkdownElement>();
        for (var i : list) {
            listNew.add((MarkdownElement) i);
        }
        this.inside = listNew;
    }
}
