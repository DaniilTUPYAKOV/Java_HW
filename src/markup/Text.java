package markup;

public class Text implements MarkdownElement {
    private String innerText;

    public Text(String str) {
        innerText = str;
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(innerText);
    }

    public void toBBCode(StringBuilder stringBuilder) {
        this.toMarkdown(stringBuilder);
    }
}
