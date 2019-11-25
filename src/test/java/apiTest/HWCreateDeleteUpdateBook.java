/*** homework #8 api/* Create call to create,update and delete a book and Assert it.
 */

package apiTest;

import com.google.gson.Gson;
import models.*;
import com.google.gson.Gson;
import models.*;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class HWCreateDeleteUpdateBook extends BaseTest{

@Test

    public void createBook() {

    Book book = new Book(0, "Java 2: The Complete Reference, Fifth Edition", "Herbert Schildt", "education", "new");
    String payload = new Gson().toJson(book);

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

    Assert.assertEquals(response.statusCode(), 200);

    JsonPath json = response.jsonPath();
    BookCreateResponse bookCreateResponse = json.getObject("$", BookCreateResponse.class);
    Assert.assertEquals(bookCreateResponse.getErrors().size(), 0);
    Assert.assertTrue(bookCreateResponse.getValue() > 0);
    System.out.println(bookCreateResponse.getValue());
}
    @Test

    public void updateBook() {
        Book updateBook = new Book(267, "Java 2: The Complete Reference, Fifth Edition", "Herbert Schildt,", "education", "used");
        String payload = new Gson().toJson(updateBook);

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/267")
                .headers(headers)
                .body(payload)
                .when()
                .put()
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();
        json.prettyPrint();
        BookUpdateResponse bookUpdateResponse = json.getObject("$", BookUpdateResponse.class);
        Assert.assertEquals(bookUpdateResponse.getErrors().size(), 0);
        Assert.assertTrue(bookUpdateResponse.value);
    }

    @Test
           public void deleteNonExistingBook() {

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/777888")
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();


        JsonPath json = response.jsonPath();
        BookDeleteResponse bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertFalse(bookDeleteResponse.value);
    }
}






