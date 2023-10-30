package markup;

public abstract class ParagraphAndList extends AbstractElement {

    private String OpenMarker;
    private String CloseMarker;

    public ParagraphAndList(String bbCodeOpenMarker, String bbCodeCloseMarker) {

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
