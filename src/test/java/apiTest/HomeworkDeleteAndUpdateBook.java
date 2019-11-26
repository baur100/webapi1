package apiTest;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookUpdateResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.given;

public class HomeworkDeleteAndUpdateBook extends BaseTest {

    @Test
    public void deleteNonExistingBook (){

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/1000")
                .headers(headers)
        .when()
                .delete()
        .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(),404);

        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);

        // Assert.assertEquals(bookDeleteResponse.getErrors().size(),1)
        // Assert.assertEquals(bookDeletet.Response.gerErrors().get(0), "The book record couldn't be found.")
        Assert.assertEquals(bookDeleteResponse.getErrors().toString(),"[The book record couldn't be found.]");
        Assert.assertFalse(bookDeleteResponse.value);
        }

        @Test

        public void updateBook () {

            //Arrange
            Book book = generateRandomBook();
            String payload = new Gson().toJson(book);

            //Act
            Response response = given()
                    .baseUri(baseUrl)
                    .basePath("api/books/id/148")
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
            var bookUpdateResponse = json.getObject("$", BookUpdateResponse.class);
            Assert.assertEquals(bookUpdateResponse.getErrors().size(),0);
            Assert.assertTrue(bookUpdateResponse.value);
            Assert.assertNotNull(bookUpdateResponse.value);
            }

}
