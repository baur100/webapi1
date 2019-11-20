package models;

import java.util.List;

public class BookRersponse {
    private Book value;
    private List<String> errors;

    public BookRersponse(Book value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public Book getValue() {
        return value;
    }

    public void setValue(Book value) {
        this.value = value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
