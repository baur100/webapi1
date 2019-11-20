package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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

        Response response = given()
                .baseUri(url.toString())
               // .basePath("api/books/id/240")
                .header("Authorization","Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
                .header("Content-type", "application/JSON")
                .contentType(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        BookResponse bookResponse = json.getObject("$", BookResponse.class);

        Assert.assertTrue(true);


    }
}
