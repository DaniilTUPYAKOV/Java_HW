import java.util.Map;

public class Service {

    static final Map<TagType, String> tags = Map.of(
        TagType.HEADER, "h",
        TagType.PARAGRAPH, "p",
        TagType.EMPHASISE, "em",
        TagType.STRONG, "strong",
        TagType.STRIKEOUT, "s",
        TagType.CODE, "code",
        TagType.TEXT, "");

    static void addTag(StringBuilder targetText, TagType type, boolean closer, int headerOrder){
        targetText.append('<');
        if (closer) {
            targetText.append('/');
        }
        targetText.append(tags.get(type));
        if (headerOrder > 0) {
            targetText.append(Integer.toString(headerOrder));
        }
        targetText.append('>');
    }
}
