package apiTest;

import com.google.gson.Gson;
import helpers.DbAdapter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookDeleteResponse;
import models.UsersBook;
import models.UsersBookAddResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class TestUsersBook extends BaseTest {
    int bookId;
    int idUsersBook;
    private final String userId="aa22b9b2-9160-432b-91c1-b2c6466d8dfb";

    @BeforeMethod
    public void startUp() throws SQLException {
        List<Book> books= DbAdapter.getAllBooks();

        List<Integer> bookIds= books.stream().map(Book::getId).collect(Collectors.toList());
        List<UsersBook> ubList = DbAdapter.getAllUsersBooks();

        List<Integer>allowedBooks = new ArrayList<Integer>();
        for (int id:bookIds){
            if(ubList.stream().noneMatch(x->x.getBookId()==id))
                allowedBooks.add(id);
        }
        //==============================
        var random = new Random();
        bookId = allowedBooks.get(random.nextInt(allowedBooks.size()));
    }
    @AfterMethod
    public void tearDown() throws SQLException {
        DbAdapter.deleteUsersBookFromDb(idUsersBook);
    }

    @Test
    public void addBookToUser() throws SQLException {
        var usersBook = new UsersBook();
        usersBook.setUserId(userId);
        usersBook.setBookId(bookId);

        String payload = new Gson().toJson(usersBook);

        //Act
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/users/books/access/add")
                .headers(headers)
                .body(payload)
                .when()
                .put()
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.getStatusCode(),200);

        JsonPath json = response.jsonPath();
        var ubResponse = json.getObject("$", UsersBookAddResponse.class);

        Assert.assertEquals(ubResponse.getErrors().size(),0);
        Assert.assertNotNull(ubResponse.getValue()>0);
        idUsersBook = ubResponse.getValue();

        var usersBookFromDb = DbAdapter.getUsersBookById(idUsersBook);
        Assert.assertEquals(usersBookFromDb.getBookId(),bookId);
        Assert.assertEquals(usersBookFromDb.getUserId().toString(),userId);
    }
}
