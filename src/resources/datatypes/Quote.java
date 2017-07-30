package resources.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Quote {
    private String author;
    private String source;
    private String quote;
    private List<String> tags;

    public Quote(String author, String source, String quote) {
        this.author = author;
        this.source = source;
        this.quote = quote;
        this.tags = new ArrayList<>();
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public List<String> getTags() { return tags; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
