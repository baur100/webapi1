package apiTest;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookUpdateResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.given;

public class CreateUpdateDeleteTest extends BaseTest {


    @Test
    public void createBook() {
        //Arrange
        var newBook = generateRandomBook();
        String payload = new Gson().toJson(newBook);

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
        BookCreateResponse bookCreateResponse = json.getObject("$", BookCreateResponse.class);
        Assert.assertEquals(bookCreateResponse.getErrors().size(), 0);
        Assert.assertTrue(bookCreateResponse.getValue() > 0);
        System.out.println(bookCreateResponse.getValue());
    }


    @Test
    public void deleteBook() {
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/274")
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
    }

    //Home Work
    @Test
    public void deleteNotExistingBook() {
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/276")
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
    }


    @Test
    public void updateBook() {

        var updatedBook = generateRandomBook();
        String payload = new Gson().toJson(updatedBook);

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/276")
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
        var bookUpdateResponse = json.getObject("$", BookUpdateResponse.class);
        Assert.assertEquals(bookUpdateResponse.getErrors().size(), 0);
        Assert.assertTrue(bookUpdateResponse.getValue(),"Book info wasn't updated");

    }
}
