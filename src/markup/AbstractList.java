package markup;

import java.util.List;

public abstract class AbstractList extends ParagraphAndList {

    protected AbstractList(List<ListItem> list, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(list, bbCodeOpenMarker, bbCodeCloseMarker);
    }
}
