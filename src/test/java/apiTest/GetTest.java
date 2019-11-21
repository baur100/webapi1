package apiTest;

import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import models.Book;
import models.BookResponse;
import models.GetAllBooksResponse;
import org.apache.http.client.utils.URIBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;


import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

public class GetTest extends BaseTest{

    @Test
            public void testGet_getABookById() {

        Response response =
                given()
                    .baseUri(baseUrl)
                    .basePath("api/books/id/240")
                    .headers(headers)
                .when()
                    .get()
                .then()
                    .extract()
                    .response();

        Assert.assertEquals(response.getStatusCode(),200);

        JsonPath json = response.jsonPath();

        //we can retrive only part of response

        Book book = json.getObject("value",Book.class);
        Assert.assertEquals(book.getId(),240);
        Assert.assertEquals(book.getAuthor(),"XXstring");
        Assert.assertEquals(book.getCondition(),"XXstring");
        Assert.assertEquals(book.getGenre(),"XXstring");
        Assert.assertEquals(book.getLabel(),"XXstring");




        BookResponse bookResponse = json.getObject("$",BookResponse.class);

        //Assertion that array Errors is empty

        Assert.assertEquals(bookResponse.getErrors().size(),0);

        //assert that if of retrived book is 240
        Assert.assertEquals(bookResponse.getValue().getId(),240);

        Assert.assertEquals(bookResponse.getValue().getLabel(),"XXstring");
        Assert.assertEquals(bookResponse.getValue().getAuthor(),"XXstring");
        Assert.assertEquals(bookResponse.getValue().getGenre(),"XXstring");
        Assert.assertEquals(bookResponse.getValue().getCondition(),"XXstring");



    }
    @Test
    public void getAllBooks(){
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/all")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(),200);



        JsonPath json = response.jsonPath();


        GetAllBooksResponse getAllBooksRespons = json.getObject("$",GetAllBooksResponse.class);


        //Assertion that array Errors is empty

        Assert.assertEquals(getAllBooksRespons.getErrors().size(),0);
        Assert.assertEquals(getAllBooksRespons.getValue().size(),62);

        var value= getAllBooksRespons.getValue().get(2);

        Assert.assertEquals(value.getId(),5);
        Assert.assertEquals(value.getAuthor(),"Robert Shekley");
        Assert.assertEquals(value.getCondition(),"used");
        Assert.assertEquals(value.getGenre(),"science fiction");
        Assert.assertEquals(value.getLabel(),"Lalangamena");


    }


}
