package apiTest;

import helpers.DBAdaptor;
import helpers.DbAdapter;
import models.Book;
import models.UsersBook;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TestUsersBook {
    @BeforeMethod
    Book book;
    public void startUp() throws SQLException {
        book = new Book();
        List<Book> books= DbAdapter.getAllBooks();
        List<Integer> bookIds=books.stream().map(x->x.getId()).collect(Collectors.toList());

        List<UsersBook> ubList = DbAdapter.

    }
    @Test
    public void addBookToUser(){

    }
}
