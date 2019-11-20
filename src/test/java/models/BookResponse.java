package models;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {
    private Book value;
    private ArrayList<String> errors = new;

    public BookResponse(Book value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setValue(Book value) {
        this.value = value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public Book getValue() {
        return value;
    }
}
