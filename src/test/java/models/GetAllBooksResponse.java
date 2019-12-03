package models;


import java.util.List;

public class GetAllBooksResponse {

        private List<Book> value;
        private List<String> errors;

    public GetAllBooksResponse(List<Book> value, List<String> errors) {
        this.value = value;
        this.errors = errors;
    }

    public List<Book> getValue() {
        return value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setValue(List<Book> value) {
        this.value = value;
    }
}
