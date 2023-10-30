package markup;

public abstract class MarkerElement extends AbstractElement{

    private String markDownMarker;
    private String bbCodeOpenMarker;
    private String bbCodeCloseMarker;

    public MarkerElement(String markDownMarker, String bbCodeOpenMarker, String bbCodeCloseMarker) {

        this.bbCodeOpenMarker = bbCodeOpenMarker;
        this.bbCodeCloseMarker = bbCodeCloseMarker;
        this.markDownMarker = markDownMarker;
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.mark(stringBuilder, markDownMarker, markDownMarker, MarkType.MARKDOWN);
    }

    public void toBBCode(StringBuilder stringBuilder) {
        super.mark(stringBuilder,bbCodeOpenMarker, bbCodeCloseMarker, MarkType.BBCODE);
    }
    
}
