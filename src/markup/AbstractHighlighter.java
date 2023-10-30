package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHighlighter extends ParagraphElement {

    public AbstractHighlighter(List<?extends AbstractElement> list, String markDownMarker, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(markDownMarker, bbCodeOpenMarker, bbCodeCloseMarker);
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
    }
}
