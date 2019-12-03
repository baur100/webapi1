package models;

import java.util.List;

public class BookCreateResponse {
    private int value;
    private List<String> errors;

    public BookCreateResponse(int value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getvalue() {
        return value;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setId(int value) {
        this.value = value;
    }
}
