package models;

import java.util.ArrayList;

public class AllBooksResponse {
    private ArrayList<Book> value=new ArrayList<>();
    private ArrayList<String> errors=new ArrayList<>();

    public AllBooksResponse(ArrayList<Book> value, ArrayList<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public void setValue(ArrayList<Book> value) {
        this.value = value;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public ArrayList<Book> getValue() {
        return value;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

}
