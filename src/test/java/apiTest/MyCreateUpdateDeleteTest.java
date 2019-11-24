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

public class MyCreateUpdateDeleteTest extends BaseTest{
    @Test
    public void createBook(){
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
        Assert.assertEquals(response.statusCode(),200);

        JsonPath json = response.jsonPath();
        var bookCreateResponse = json.getObject("$",BookCreateResponse.class);
        Assert.assertEquals(bookCreateResponse.getErrors().size(),0);
        Assert.assertTrue(bookCreateResponse.getValue()>0);
        System.out.println(bookCreateResponse.getValue());
    }

    @Test
    public void deleteBook(){
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/253")
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
    }

    @Test
    public void deleteNonExistingBook(){
        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/500")
                .headers(headers)
                .when()
                .delete()
                .then()
                .extract()
                .response();
        Assert.assertEquals(response.statusCode(),404);
    }

    @Test
    public void updateBook(){

        Book updateBook =new Book();
        updateBook.setLabel("The Great Gatsby");
        updateBook.setAuthor("F. Scott Fitzgerald");
        updateBook.setGenre("Tragedy");
        updateBook.setCondition("new");
        String updatedBook = new Gson().toJson(updateBook);

        Response response = given()
                .baseUri(baseUrl)
                .basePath("api/books/id/258")
                .headers(headers)
                .body(updatedBook)
                .when()
                .put()
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        json.prettyPrint();

        var bookUpdateResponse=json.getObject("$", BookUpdateResponse.class);
        Assert.assertTrue(bookUpdateResponse.value);
        Assert.assertEquals(bookUpdateResponse.getErrors().size(),0);
    }

}
