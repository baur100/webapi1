package models;

import java.util.ArrayList;

public class GetAllBooksResponse {
    private ArrayList<Book> value;
    private ArrayList<String>errors;

    public GetAllBooksResponse(ArrayList<Book> value, ArrayList<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public ArrayList<Book> getValue() {
        return value;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setValue(ArrayList<Book> value) {
        this.value = value;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
}
