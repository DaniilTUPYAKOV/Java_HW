package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends ParagraphAndList {

    public Paragraph(List<? extends ParagraphElement> list) {
        super("", "");
        List<MarkdownElement> listNew = new ArrayList<MarkdownElement>();
        for (var i : list) {
            listNew.add((MarkdownElement) i);
        }
        this.inside = listNew;
    }
    
}
