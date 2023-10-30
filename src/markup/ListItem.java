package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends MarkerElement {

    public ListItem(List<ParagraphAndList> list) {
        super("", "[*]", "");
        List<MarkableElement> listNew = new ArrayList<MarkableElement>();
        for (var i : list) {
            listNew.add((MarkableElement) i);
        }
        this.inside = listNew;
    }

}
