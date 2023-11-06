// package structClasses;

public class Text extends ParagraphElement {

    public Text(StringBuilder str) {
        super(null, TagType.TEXT);
        text = str;
    }
    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }
}
