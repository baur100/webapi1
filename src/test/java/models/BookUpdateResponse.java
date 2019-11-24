package models;

import java.util.List;

public class BookUpdateResponse {
    public boolean value;
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
}
