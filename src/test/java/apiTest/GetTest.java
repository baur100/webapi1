package apiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookResponse;
import models.GetAllBooksResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetTest extends BaseTest {
    @Test
    public void testGet_getBookById() throws URISyntaxException {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/239")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200);


        JsonPath json = response.jsonPath();
        //1
        BookResponse bookResponse = response.jsonPath().getObject("$", BookResponse.class);
        //1
        Assert.assertEquals(bookResponse.getErrors().size(), 0);
        Assert.assertEquals(bookResponse.getValue().getId(), 239);
        Assert.assertEquals(bookResponse.getValue().getLabel(), "I Think Therefore I Play");
        Assert.assertEquals(bookResponse.getValue().getAuthor(), "Andrea Pirlo");
        Assert.assertEquals(bookResponse.getValue().getGenre(), "Sport, autobiographical");

        //2
        Book book = json.getObject("value", Book.class);
        //2
        Assert.assertEquals(book.getId(), 239);
        Assert.assertEquals(book.getLabel(), "I Think Therefore I Play");
        Assert.assertEquals(book.getAuthor(), "Andrea Pirlo");
        Assert.assertEquals(book.getGenre(), "Sport, autobiographical");
    }


    //Home Work
    @Test
    public void getAllBooks() {
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/All")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath json = response.jsonPath();
        GetAllBooksResponse allBooksResponse = json.getObject("$", GetAllBooksResponse.class);

        Assert.assertEquals(allBooksResponse.getErrors().size(), 0);
        Assert.assertEquals(allBooksResponse.getValue().get(1).getId(), 3);

        var someBook = allBooksResponse.getValue().stream().filter(x -> x.getId() == 239).findFirst();
        Assert.assertEquals(someBook.get().getLabel(), "I Think Therefore I Play");
        Assert.assertEquals(someBook.get().getId(), 239, "No such id");

    }
}
/*

    @Test
    public void testGet_getBookById() throws URISyntaxException {

*/
/*
        URIBuilder url = new URIBuilder();
        url.setScheme("http")
                .setHost("booklibrarywebapidev.azurewebsites.net")
                .setPath("api/books/id/239")
                .setPort(80)
                .build();
*//*



        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/239")
//                        .baseUri(url.toString())
//                        .header("Authorization", "Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
//                        .header("Content-Type","application/JSON")
                        .headers(headers)
//                        .contentType(ContentType.JSON)
                        .when()
                        .get()
                        .then()
//                        .statusCode(200)
                        .extract()
                        .response();
        */
/* System.out.println(response);*//*


        Assert.assertEquals(response.getStatusCode(), 200);


        JsonPath json = response.jsonPath();
        //1
        BookResponse bookResponse = response.jsonPath().getObject("$", BookResponse.class);
        //1
        Assert.assertEquals(bookResponse.getErrors().size(), 0);
        Assert.assertEquals(bookResponse.getValue().getId(), 239);
        Assert.assertEquals(bookResponse.getValue().getLabel(), "I Think Therefore I Play");
        Assert.assertEquals(bookResponse.getValue().getAuthor(), "Andrea Pirlo");
        Assert.assertEquals(bookResponse.getValue().getGenre(), "Sport, autobiographical");

        //2
        Book book = json.getObject("value", Book.class);
        //2
        Assert.assertEquals(book.getId(), 239);
        Assert.assertEquals(book.getLabel(), "I Think Therefore I Play");
        Assert.assertEquals(book.getAuthor(), "Andrea Pirlo");
        Assert.assertEquals(book.getGenre(), "Sport, autobiographical");

    }
*/


