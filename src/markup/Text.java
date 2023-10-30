package markup;

public class Text extends ParagraphElement {

    public Text(String str) {
        super(null, "", "", "");
        text = str;
    }
    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }
    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        this.toMarkdown(stringBuilder);
    }
}
