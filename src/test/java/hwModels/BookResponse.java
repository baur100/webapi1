package hwModels;

import java.util.List;

public class BookResponse {
    private Book value;
    private List<String> errors;

    public BookResponse(Book book, List<String> errors) {
        this.value = book;
        this.errors = errors;
    }

    public void setBook(Book book) {
        this.value = book;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Book getBook() {
        return value;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
