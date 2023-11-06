// package structClasses;

import java.util.List;

public abstract class MarkerElement extends AbstractElement{

    protected MarkerElement(List<?extends AbstractElement> list, TagType type) {
        super(list, type);
    }
    
}
