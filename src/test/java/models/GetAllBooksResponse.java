package models;

import java.util.List;

public class GetAllBooksResponse {
    private List<Book> value;
    private List<String> errors;

    public List<Book> getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }
}
