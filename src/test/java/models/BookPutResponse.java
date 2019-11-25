package models;

import com.sun.istack.Nullable;

import java.util.List;

public class BookPutResponse {
    @Nullable
    public boolean value;
//    public Book value;
    private List<String> errors;

//    public Book getValue() {
//        return value;
//    }

//    public void setValue(Book value) {
//        this.value = value;
//    }

    public List<String> getErrors() {
        return errors;
    }


}

