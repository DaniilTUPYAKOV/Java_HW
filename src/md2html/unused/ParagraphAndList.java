// package structClasses;

import java.util.List;

public abstract class ParagraphAndList extends AbstractElement {

    protected ParagraphAndList(List<?extends AbstractElement> list, TagType type) {
        super(list, type);
    }
    
}
