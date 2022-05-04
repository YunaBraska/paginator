package berlin.yuna.paginator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SavePageRequest extends BaseRequest {

    @JsonProperty("content")
    private String content;

    public String getContent() {
        return content;
    }

    public SavePageRequest setContent(final String content) {
        this.content = content;
        return this;
    }
}
