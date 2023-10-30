package markup;

import java.util.ArrayList;
import java.util.List;

public class AbstractHighlighter extends ParagraphElement {

    public AbstractHighlighter(List<?extends AbstractElement> list, String markDownMarker, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(markDownMarker, bbCodeOpenMarker, bbCodeCloseMarker);
        List<MarkdownElement> listNew = new ArrayList<MarkdownElement>();
        for (var i : list) {
            listNew.add((MarkdownElement) i);
        }
        this.inside = listNew;
    }
}
