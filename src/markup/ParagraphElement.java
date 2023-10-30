package markup;

import java.util.List;

public abstract class ParagraphElement extends MarkerElement {

    public ParagraphElement(List<?extends AbstractElement> list, String markDownMarker, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(list, markDownMarker, bbCodeOpenMarker, bbCodeCloseMarker);
    }
    
}
