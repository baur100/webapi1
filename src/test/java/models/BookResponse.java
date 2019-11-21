package models;

import java.util.List;

public class BookResponse {
    private Book value;
    private List<String> errors;

    public BookResponse(Book value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public void setValue(Book value) {
        this.value = value;
    }

    public Book getValue() {
        return value;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
