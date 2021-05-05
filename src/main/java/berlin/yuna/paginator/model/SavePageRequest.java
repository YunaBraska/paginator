package berlin.yuna.paginator.model;

public class SavePageRequest {
    private String url;
    private String content;

    public String getUrl() {
        return url;
    }

    public SavePageRequest setUrl(final String url) {
        this.url = url;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SavePageRequest setContent(final String content) {
        this.content = content;
        return this;
    }
}
