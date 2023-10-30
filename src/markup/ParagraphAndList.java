package markup;

import java.util.ArrayList;
import java.util.List;

public abstract class ParagraphAndList extends AbstractElement {

    private String OpenMarker;
    private String CloseMarker;

    // protected ParagraphAndList(String bbCodeOpenMarker, String bbCodeCloseMarker) {

    //     this.OpenMarker = bbCodeOpenMarker;
    //     this.CloseMarker = bbCodeCloseMarker;
    // }
    protected ParagraphAndList(List<?extends AbstractElement> list, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
        this.OpenMarker = bbCodeOpenMarker;
        this.CloseMarker = bbCodeCloseMarker;
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.mark(stringBuilder, "", "", MarkType.MARKDOWN);
    }

    public void toBBCode(StringBuilder stringBuilder) {
        super.mark(stringBuilder,OpenMarker, CloseMarker, MarkType.BBCODE);
    }
    
}
