package resources.datatypes.bibliographicdata;

import java.time.LocalDateTime;

public class MLACitation {
    private String author;
    private String[] multipleAuthors;
    private String sourceTitle;
    private String containerTitle;
    private String otherContributors;
    private String version;
    private String number;
    private String publisher;
    private LocalDateTime publicationDate;
    private String location;
    private String translator;

    public String[] getMultipleAuthors() {
        return multipleAuthors;
    }

    public void setMultipleAuthors(String[] multipleAuthors) {
        this.multipleAuthors = multipleAuthors;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getContainerTitle() {
        return containerTitle;
    }

    public void setContainerTitle(String containerTitle) {
        this.containerTitle = containerTitle;
    }

    public String getOtherContributors() {
        return otherContributors;
    }

    public void setOtherContributors(String otherContributors) {
        this.otherContributors = otherContributors;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
