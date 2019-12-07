package apiTest;

import com.google.gson.Gson;
import helpers.DataGenerator;
import helpers.DbAdapter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.given;

public class TestUsingDb extends BaseTest {
    private Book book;

    private void init(){
        book = DataGenerator.generateRandomBook();
        try{
            var id = DbAdapter.addBookToDb(book);
            book.setId(id);
        } catch(SQLException error){
            System.out.println(error.getErrorCode());
        }
    }
    @AfterMethod
    public void tearDown(){
        try {
            DbAdapter.deleteBookFromDb(book.getId());
        } catch(SQLException error){
            System.out.println(error.getErrorCode());
        }
    }

    @Test
    public void createBook() throws SQLException {
        //Arrange
        book = generateRandomBook();
        String payload = new Gson().toJson(book);

        //Act
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books")
                .headers(headers)
                .body(payload)
                .when()
                .post()
                .then()
                .extract()
                .response();

        //Assert
        Assert.assertEquals(response.statusCode(),200);

        JsonPath json = response.jsonPath();
        var bookCreateResponse = json.getObject("$", BookCreateResponse.class);
        Assert.assertEquals(bookCreateResponse.getErrors().size(),0);
        Assert.assertTrue(bookCreateResponse.getValue()>0);
        book.setId(bookCreateResponse.getValue());

        Assert.assertTrue(DbAdapter.bookExistInDb(book.getId()));

        Book bookFromDb = DbAdapter.getBookById(book.getId());
        Assert.assertEquals(bookFromDb.getAuthor(),book.getAuthor());
        Assert.assertEquals(bookFromDb.getGenre(),book.getGenre());
        Assert.assertEquals(bookFromDb.getLabel(),book.getLabel());
        Assert.assertEquals(bookFromDb.getCondition(),book.getCondition());
    }

    @Test
    public void getBookById(){
        init();
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/"+book.getId())
                .headers(headers)
                .when()
                .get()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(),200);

        JsonPath json = response.jsonPath();
        BookResponse bookResponse = json.getObject("$",BookResponse.class);

        Assert.assertEquals(bookResponse.getValue().getLabel(),book.getLabel());
        Assert.assertEquals(bookResponse.getValue().getGenre(),book.getGenre());
        Assert.assertEquals(bookResponse.getValue().getAuthor(),book.getAuthor());
        Assert.assertEquals(bookResponse.getValue().getCondition(),book.getCondition());
    }

    @Test
    public void updateBook() throws SQLException {
        //Arrange
        init();
        Book book1 = generateRandomBook();
        String payload = new Gson().toJson(book1);

        //Act
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/"+book.getId())
                .headers(headers)
                .body(payload)
                .when()
                .put()
                .then()
                .extract()
                .response();

        //Assert
        Assert.assertEquals(response.statusCode(),200);

        JsonPath json = response.jsonPath();
        var bookResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookResponse.getErrors().size(),0);
        Assert.assertNotNull(bookResponse.value);
        Assert.assertTrue(bookResponse.value);
        //TODO Homework: get Book from DB and assert it is changed assert equal to `book1` except id

        Book bookFromDb = DbAdapter.getBookById(book.getId());
        Assert.assertEquals(bookFromDb.getAuthor(),book1.getAuthor());
        Assert.assertEquals(bookFromDb.getGenre(),book1.getGenre());
        Assert.assertEquals(bookFromDb.getLabel(),book1.getLabel());
        Assert.assertEquals(bookFromDb.getCondition(),book1.getCondition());
    }

    @Test
    public void deleteBook() throws SQLException {
        init();
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/"+book.getId())
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(),200);

        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().size(),0);
        Assert.assertNotNull(bookDeleteResponse.value);
        Assert.assertTrue(bookDeleteResponse.value);

        Assert.assertFalse(DbAdapter.bookExistInDb(book.getId()));
    }
}
