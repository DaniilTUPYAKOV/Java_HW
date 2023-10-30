package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends ParagraphAndList {

    public Paragraph(List<? extends ParagraphElement> list) {
        super("", "");
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
    }
    
}
