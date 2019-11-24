package apiTest;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUpdateDeleteTest extends BaseTest{
        @Test
    public  void createBook(){
            Book book = generateRandomBook();
            Book book = new Book(0, "H, I'm a book", "Some author", "a genre", "very bad");
            String payload=new Gson().toJson(book);
            Response response = given().baseUri(baseUrl).basePath("api/books").headers(headers).body(payload).when().post().then().extract().response();
            Assert.assertEquals(response.statusCode(),200);
            JsonPath json=response.jsonPath();
            var bookCreateResponse = json.getObject("$", BookCreateResponse.class);
            Assert.assertEquals(bookCreateResponse.getErrors().size(), 0);
        }


}
