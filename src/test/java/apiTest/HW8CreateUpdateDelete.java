package apiTest;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookPutResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HW8CreateUpdateDelete extends BaseTest {
    @Test
    public void createBook() {
        //Arrange
        Book book = generateRandomBook();
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
        System.out.println(bookCreateResponse.getValue());
    }

    @Test
    public void deleteBook() {
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/253")
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
                .basePath("api/books/id/272")
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 404);

        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().toString(), "[The book record couldn't be found.]");
        Assert.assertNotNull(bookDeleteResponse.value);
        Assert.assertEquals(bookDeleteResponse.value, false);
    }

    @Test
    public void updateBook() {

// Arrange
        Book book = new Book(271, "the Idiot-2", "F.Dostoevsky", "NewNovel", "fresh");
        String payload = new Gson().toJson(book);

// Act
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/271")
                .headers(headers)
                .body(payload)
        .when()
                .put()
        .then()
                .extract()
                .response();
// Assert
        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();
        var bookPutResponse = json.getObject("$", BookPutResponse.class);
        Assert.assertEquals(bookPutResponse.getErrors().size(), 0);

        Assert.assertEquals(bookPutResponse.value, true);

    }

    @Test
    public void updateRandomBook() {
        Book book = generateRandomBook();
        String payload = new Gson().toJson(book);

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/195")
                .headers(headers)
                .body(payload)
        .when()
                .put()
        .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();
        var bookPutResponse = json.getObject("$", BookPutResponse.class);

        Assert.assertEquals(bookPutResponse.getErrors().size(), 0);
        Assert.assertEquals(bookPutResponse.value, true);
    }
}