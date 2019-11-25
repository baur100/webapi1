package models;

import java.util.List;

public class BookUpdateResponse {
    private boolean value;
    private List<String> errors;

    public boolean getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }
}
