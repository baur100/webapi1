package models;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {
    private Book value;
    private ArrayList<String> errors=new ArrayList<>();

    public BookResponse(Book value, ArrayList<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public void setBook(Book book) {
        this.value = book;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public Book getBook() {
        return value;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
