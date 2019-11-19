package models;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {
    private Book value;
    private ArrayList<String> errors = new ArrayList<>();

    public BookResponse(Book value, ArrayList<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public void setValue(Book value) {
        this.value = value;
    }

    public Book getValue() {
        return value;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
