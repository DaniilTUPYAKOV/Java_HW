package markup;

import java.util.List;

public abstract class AbstractHighlighter extends ParagraphElement {

    public AbstractHighlighter(List<?extends AbstractElement> list, String markDownMarker, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(list, markDownMarker, bbCodeOpenMarker, bbCodeCloseMarker);
    }
}
