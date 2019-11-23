package apiTest;

import io.restassured.path.json.JsonPath;
import models.AllBooksResponse;
import models.Book;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.net.URISyntaxException;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class GetTest extends BaseTest {


    @Test
    public void testGet_getABookById() throws URISyntaxException {
//        URIBuilder url = new URIBuilder();
//        url.setScheme("http")
//                .setHost("booklibrarywebapidev.azurewebsites.net")
//                .setPath("api/books/id/240")
//                .setPort(80)
//                .build();

        Response response =
                given()
                        .baseUri(basUrl)
                        .basePath("api/books/id/240")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        json.prettyPrint();

        BookResponse bookResponse = json.getObject("$", BookResponse.class);
        Book book = json.getObject("value", Book.class);
        Assert.assertEquals(book.getId(), 240);
        Assert.assertEquals(book.getAuthor(), "XXstring");
        Assert.assertEquals(book.getCondition(), "XXstring");
        Assert.assertEquals(book.getGenre(), "XXstring");
        Assert.assertEquals(book.getLabel(), "XXstring");
        Assert.assertEquals(bookResponse.getErrors().size(), 0);
        Assert.assertEquals(bookResponse.getValue().getId(), 240);
        Assert.assertEquals(bookResponse.getValue().getLabel(), "XXstring");
        Assert.assertEquals(bookResponse.getValue().getAuthor(), "XXstring");
        Assert.assertEquals(bookResponse.getValue().getGenre(), "XXstring");
        Assert.assertEquals(bookResponse.getValue().getCondition(), "XXstring");
    }

    }
