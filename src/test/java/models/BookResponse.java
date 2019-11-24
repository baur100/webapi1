package models;

import java.util.ArrayList;
import java.util.List;

public class BookResponse {
    private Book value;
    private ArrayList<String> errors;

    public Book getValue() {
        return value;
    }
    public List<String> getErrors() {
        return errors;
    }
}
