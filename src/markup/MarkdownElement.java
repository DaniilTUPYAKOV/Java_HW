package markup;

public interface MarkdownElement {

    void toMarkdown(StringBuilder stringBuilder);
    void toBBCode(StringBuilder stringBuilder);
}