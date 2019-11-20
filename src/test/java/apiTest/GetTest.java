package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.BookRersponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetTest {

    @Test
    public void testGet_getBookById() {
        Response response = given()
                .baseUri("http://booklibrarywebapidev.azurewebsites.net/")
                .basePath("/api/books/id/232")
                .header("Authorization", "Basic YWJjQHh5ei5jb206VGVzdHRlc3QxMjMh")
                .contentType(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                    .extract()
                    .response();
// Parser:
        JsonPath json = response.jsonPath();
        BookRersponse bookRersponse = json.getObject("$", BookRersponse.class);
//        переводим наш файл из json в объект класса bookResponse

        Assert.assertTrue(true);
    }

}
