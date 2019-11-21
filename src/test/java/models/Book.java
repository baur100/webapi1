package models;

public class Book {
    private int id;
    private String label;
    private String author;
    private String genre;
    private String condition;

    public Book(int id, String label, String author, String genre, String condition) {
        this.id = id;
        this.label = label;
        this.author = author;
        this.genre = genre;
        this.condition = condition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getLabel() {
        return label;
    }

    public String getCondition() {
        return condition;
    }

    public String getGenre() {
        return genre;
    }
}
