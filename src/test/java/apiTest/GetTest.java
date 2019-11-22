package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.Book;
import models.BookResponse;
import org.apache.http.client.utils.URIBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;


public class GetTest {


    @Test
    public void testGet_getABookById() throws URISyntaxException {
        URIBuilder url = new URIBuilder();
        url.setScheme("http")
                .setHost("booklibrarywebapidev.azurewebsites.net")
                .setPath("api/books/id/240")
                .setPort(80)
                .build();

        Response response =
                given()
                    .baseUri(url.toString())
//                .basePath("api/books/id/240")
                    .header("Authorization","Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
                    .header("Content-Type","application/JSON")
                    .contentType(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        JsonPath json = response.jsonPath();

        //json.prettyPrint();


       BookResponse bookResponse = json.getObject("$",BookResponse.class);
       Book book = json.getObject("value", Book.class);
       Assert.assertEquals(book.getId(), 240);

//        Assert.assertEquals(bookResponse.getErrors().size(),0);
//        Assert.assertEquals(bookResponse.getValue().getId(), 240);
//

    }
}
