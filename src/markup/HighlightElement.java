package markup;

import java.util.List;

public abstract class HighlightElement extends AbstractElement{

    private String markDownMarker;
    private String bbCodeOpenMarker;
    private String bbCodeCloseMarker;

    public HighlightElement(List<MarkdownElement> list, String markDownMarker, String bbCodeOpenMarker, String bbCodeCloseMarker) {
        super(list);
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
