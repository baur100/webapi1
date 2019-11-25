package models;

import java.util.List;

public class BookResponse {
    private Book value;
    private List<String> errors;
/*

    public BookResponse(Book value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }
*/

    public Book getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }
}
