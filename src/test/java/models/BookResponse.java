package models;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {
    private Book value;
    private ArrayList<String> errors;

    public BookResponse(Book value, ArrayList<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public void setBook(Book value) {
        this.value = value;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public Book getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }
}
