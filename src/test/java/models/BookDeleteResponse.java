package models;

import com.sun.istack.Nullable;

import java.util.List;

public class BookDeleteResponse {
    @Nullable
    public boolean value;
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
}
