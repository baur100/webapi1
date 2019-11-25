package apiTest;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.given;


public class CreateUpdateDeleteTest extends BaseTest {
    @Test
    public void createBook() {

        //Arrange

        Book book = generateRandomBook();

        // book is json and payload - is String; with help of Gson Json=>String
        String payload = new Gson().toJson(book);

        //Act

        Response response =
                given()
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

//responce come on escape format and we need transfeorm it to java class
        Assert.assertEquals(response.statusCode(),200);
        JsonPath json = response.jsonPath();
        var bookCreateResponse = json.getObject("$", BookCreateResponse.class);
        Assert.assertEquals(bookCreateResponse.getErrors().size(),0);
        Assert.assertTrue(bookCreateResponse.getvalue()>0);

        System.out.println(bookCreateResponse.getvalue());

    }


    @Test
    public void deleteBook(){
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/255")
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
        //now we assert that value is not NULL, because if it s will be null - we will have an exception
        //now we make value and not getValue, because it s boolean and it s public
        Assert.assertTrue(bookDeleteResponse.value);

    }
    @Test
    public void deleteNotExistedBook(){
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/255")
                        .headers(headers)
                .when()
                        .delete()
                .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.statusCode(),404);
        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().toString(),"[The book record couldn't be found.]");
        Assert.assertFalse(bookDeleteResponse.value);

      //  Assert.assertNull(bookDeleteResponse.value);
        //now we assert that value is not NULL, because if it s will be null - we will have an exception
        //now we make value and not getValue, because it s boolean and it s public
        //Assert.assertTrue(bookDeleteResponse.value);

    }

    @Test

    public void upDateBook(){
        //Arrange

        Book book = generateRandomBook();

        // book is json and payload - is String; with help of Gson Json=>String
        String payload = new Gson().toJson(book);

        //Act

        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/129")
                        .headers(headers)
                        .body(payload)
                        .when()
                        .put()
                        .then()
                        .extract()
                        .response();
        //Assert

//responce come on escape format and we need transfeorm it to java class
        Assert.assertEquals(response.statusCode(),200);
        JsonPath json = response.jsonPath();
        var bookPutResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookPutResponse.getErrors().size(),0);
        Assert.assertTrue(bookPutResponse.value);




    }

    }
