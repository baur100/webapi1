package apiTest;

import io.restassured.http.ContentType;

import io.restassured.response.Response;
import models.BookResponse;
import org.apache.http.client.utils.URIBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

public class GetTest {

    @Test
    public void testGet_getBookById() throws URISyntaxException {

        URIBuilder url = new URIBuilder();
        url.setScheme("http")
                .setHost("booklibrarywebapidev.azurewebsites.net")
                .setPath("api/books/id/239")
                .setPort(80)
                .build();


        Response response = given()
                /*.baseUri("http://booklibrarywebapidev.azurewebsites.net")
                .basePath("api/books/id/239")*/
                .baseUri(url.toString())
                .header("Authorization", "Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
                .contentType(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response);

        BookResponse bookResponse = response.jsonPath().getObject("$", BookResponse.class);

        Assert.assertTrue(true);
    }
}
