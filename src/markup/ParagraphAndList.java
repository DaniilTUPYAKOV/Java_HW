package markup;

import java.util.List;

public abstract class ParagraphAndList extends AbstractElement {

    private String OpenMarker;
    private String CloseMarker;

    protected ParagraphAndList(List<?extends AbstractElement> list, String OpenMarker, String CloseMarker) {
        super(list);
        this.OpenMarker = OpenMarker;
        this.CloseMarker = CloseMarker;
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.mark(stringBuilder, "", "", MarkType.MARKDOWN);
    }

    public void toBBCode(StringBuilder stringBuilder) {
        super.mark(stringBuilder,OpenMarker, CloseMarker, MarkType.BBCODE);
    }
    
}
