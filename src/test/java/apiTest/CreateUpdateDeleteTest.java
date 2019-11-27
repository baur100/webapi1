package apiTest;

import com.google.gson.Gson;
import helpers.DataGenerator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.given;

public class CreateUpdateDeleteTest extends BaseTest {
    private Book book;

    public void init() {
        book = DataGenerator.generateRandomBook();
        String payload = new Gson().toJson(book);

        Response response = given().baseUri(baseUrl).basePath("api/books").headers(headers).body(payload)
                .post().then().extract().response();
        var id = response.jsonPath().getObject("$", BookCreateResponse.class).getValue();
        book.setId(id);
    }

    @AfterMethod
    public void tearDown() {
        given().baseUri(baseUrl).basePath("api/books/id/" + book.getId())
                .headers(headers).delete();
    }

    @Test
    public void createBook() {
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
        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();
        var bookCreateResponse = json.getObject("$", BookCreateResponse.class);
        Assert.assertEquals(bookCreateResponse.getErrors().size(), 0);
        Assert.assertTrue(bookCreateResponse.getValue() > 0);
        book.setId(bookCreateResponse.getValue());
        System.out.println(bookCreateResponse.getValue());
    }

    @Test
    public void deleteBook() {
        init();
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/" + book.getId())
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().size(), 0);
        Assert.assertNotNull(bookDeleteResponse.value);
        Assert.assertTrue(bookDeleteResponse.value);
    }

    @Test
    public void deleteNonExistingBook() {
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/253")
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 404);

        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().size(), 1);
        Assert.assertFalse(bookDeleteResponse.value);
        Assert.assertEquals(bookDeleteResponse.getErrors().get(0), "The book record couldn't be found.");
    }

    @Test
    public void updateBook() {
        //Arrange
        init();
//        Book book1 = generateRandomBook();
        Book book1 = new Book(book.getId(), book.getLabel(), book.getAuthor(), book.getGenre(), "newwwww");
        String payload = new Gson().toJson(book1);

        //Act
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/" + book.getId())
                .headers(headers)
                .body(payload)
                .when()
                .put()
                .then()
                .extract()
                .response();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();
        var bookResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookResponse.getErrors().size(), 0);
        Assert.assertNotNull(bookResponse.value);
        Assert.assertTrue(bookResponse.value);
    }

    @Test
    public void getBookById() {
        init();
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/" + book.getId())
                .headers(headers)
                .when()
                .get()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath json = response.jsonPath();
        BookResponse bookResponse = json.getObject("$", BookResponse.class);

        Assert.assertEquals(bookResponse.getValue().getLabel(), book.getLabel());
        Assert.assertEquals(bookResponse.getValue().getGenre(), book.getGenre());
        Assert.assertEquals(bookResponse.getValue().getAuthor(), book.getAuthor());
        Assert.assertEquals(bookResponse.getValue().getCondition(), book.getCondition());
    }
}
