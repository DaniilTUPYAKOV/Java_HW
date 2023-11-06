// package structClasses;

import java.util.List;

public abstract class AbstractHighlighter extends ParagraphElement {

    public AbstractHighlighter(List<?extends AbstractElement> list, TagType type) {
        super(list, type);
    }
}
