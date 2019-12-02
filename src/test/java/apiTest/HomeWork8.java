package apiTest;

import com.google.gson.Gson;
import helpers.DataGenerator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookDeleteResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HomeWork8 extends BaseTest {

    @Test
    public void deleteNonExistingBook(){
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/253")
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(),404);

        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().size(),1);
        Assert.assertFalse(bookDeleteResponse.value);
        Assert.assertEquals(bookDeleteResponse.getErrors().get(0),"The book record couldn't be found.");
    }

    @Test
    public void updateBook(){
        Book book1 = DataGenerator.generateRandomBook();
        String payload = new Gson().toJson(book1);

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/231")
                .headers(headers)
                .body(payload)
                .when()
                .put()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(),200);

        JsonPath json = response.jsonPath();
        var bookResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookResponse.getErrors().size(),0);
        Assert.assertNotNull(bookResponse.value);
        Assert.assertTrue(bookResponse.value);
    }
}
