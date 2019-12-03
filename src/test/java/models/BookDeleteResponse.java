package models;

import com.sun.istack.Nullable;

import java.util.List;

public class BookDeleteResponse {
    //если у нас фуркция может вернуть нулл - то нам нужно эта анотация
    //при удалении не существующей книги мы в валуе получаем нулл

    @Nullable
    public boolean value;
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

}
