package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends MarkerElement {

    public ListItem(List<ParagraphAndList> list) {
        super("", "[*]", "");
        List<MarkdownElement> listNew = new ArrayList<MarkdownElement>();
        for (var i : list) {
            listNew.add((MarkdownElement) i);
        }
        this.inside = listNew;
    }

}
