package models;

import java.util.List;

public class BookCreateResponse {
    private int value;
    private List<String> errors;

    public int getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }
}
