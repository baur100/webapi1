package apiTest;

import com.google.gson.Gson;
import helpers.DbAdapter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.UsersBook;
import models.UsersBookAddResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;

public class UserBookOuterJoin extends BaseTest {
    int bookId;
    int idUsersBook;
    private final String userId="aa22b9b2-9160-432b-91c1-b2c6466d8dfb";

    //TODO ===========================
    @BeforeMethod
    public void startUp(){

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
        Assert.assertEquals(usersBookFromDb.getUserId(),userId);
    }
}
