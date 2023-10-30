package markup;

import java.util.List;

public class ListItem extends MarkerElement {

    public ListItem(List<ParagraphAndList> list) {
        super(list, "", "[*]", "");
    }

}
