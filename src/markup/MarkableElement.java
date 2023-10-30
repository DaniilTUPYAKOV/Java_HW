package markup;

public interface MarkableElement {

    void toMarkdown(StringBuilder stringBuilder);
    void toBBCode(StringBuilder stringBuilder);
}