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

public class GetTest extends BaseTest{
    @Test
    public void testGet_getABookById() throws URISyntaxException {
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
        BookResponse bookResponse = json.getObject("$",BookResponse.class);
        Book book = json.getObject("value", Book.class);

        Assert.assertEquals(book.getId(),240);
        Assert.assertEquals(book.getAuthor(),"XXstring");
        Assert.assertEquals(book.getCondition(),"XXstring");
        Assert.assertEquals(book.getGenre(),"XXstring");
        Assert.assertEquals(book.getLabel(),"XXstring");

        Assert.assertEquals(bookResponse.getErrors().size(),0);
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
            GetAllBooksResponse getAllBookResponse = json.getObject("$", GetAllBooksResponse.class);
            List<Book> booksList = getAllBookResponse.getValue();

            boolean result = booksList.stream().anyMatch((s) -> s.getId()==5);
            Assert.assertTrue(result);
    }
}
