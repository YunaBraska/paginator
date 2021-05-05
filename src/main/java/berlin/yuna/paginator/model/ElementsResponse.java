package berlin.yuna.paginator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElementsResponse {
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("text")
    private String text;
    @JsonProperty("selector")
    private String selector;
    @JsonProperty("attributes")
    private Map<String, String> attributes = new HashMap<>();
    @JsonProperty("children")
    private List<ElementsResponse> children = new ArrayList<>();

    public static List<ElementsResponse> from(final Elements elements) {
        return elements.stream().map(ElementsResponse::from).collect(Collectors.toList());
    }

    public static ElementsResponse from(final Element element) {
        final ElementsResponse result = new ElementsResponse()
                .setTag(element.tagName().toUpperCase())
                .setChildren(from(element.children()))
                .setText(element.ownText())
                .setSelector(element.cssSelector());
        element.attributes().forEach(attribute -> result.addAttribute(attribute.getKey(), attribute.getValue()));
        return result;
    }

    public ElementsResponse addAttribute(final String key, final String value) {
        attributes.put(key, value);
        return this;
    }

    public String getTag() {
        return tag;
    }

    public ElementsResponse setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getText() {
        return text;
    }

    public ElementsResponse setText(String text) {
        this.text = text;
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public ElementsResponse setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public List<ElementsResponse> getChildren() {
        return children;
    }

    public ElementsResponse setChildren(List<ElementsResponse> children) {
        this.children = children;
        return this;
    }

    public String getSelector() {
        return selector;
    }

    public ElementsResponse setSelector(String selector) {
        this.selector = selector;
        return this;
    }

    @Override
    public String toString() {
        return "ElementsResponse{" +
                "tag='" + tag + '\'' +
                ", text='" + text + '\'' +
                ", attributes=" + attributes.size() +
                ", children=" + children.size() +
                '}';
    }
}