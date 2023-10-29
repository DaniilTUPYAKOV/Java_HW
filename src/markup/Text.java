package markup;

public class Text implements MarkdownElement {
    private String innerText;

    public Text(String str) {
        innerText = str;
    }

    public void toMarkDown(StringBuilder stringBuilder) {
        stringBuilder.append(innerText);
    }
}
