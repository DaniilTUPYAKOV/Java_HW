package markup;

import java.util.List;

public abstract class HighlightElement extends AbstractElement{

    private String markDownMarker;
    private String bbCodeMarker;

    public HighlightElement(List<MarkdownElement> list, String markDownMarker, String bbCodeMarker) {
        super(list);
        this.bbCodeMarker = bbCodeMarker;
        this.markDownMarker = markDownMarker;
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        super.toMarkdownAbstract(stringBuilder, markDownMarker);
    }

    public void toBBCode(StringBuilder stringBuilder) {
        super.toMarkdownAbstract(stringBuilder, bbCodeMarker);
    }
    
    
}
