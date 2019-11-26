package apiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookResponse;
import models.GetAllBooksResponse;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class GetAllBooksTest extends BaseTest {
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

        Assert.assertEquals(response.statusCode(), 200);

        JsonPath json = response.jsonPath();


       GetAllBooksResponse allBooksResponse = json.getObject("$", GetAllBooksResponse.class);
        var listOfBooks = allBooksResponse.getValue();

        for (Book book:listOfBooks) {
            if(book.getId()==6){
                Assert.assertEquals(book.getId(), 6);
                Assert.assertEquals(book.getAuthor(), "Lev Tolstoy");
                Assert.assertEquals(book.getLabel(), "War and Peace");
                Assert.assertEquals(book.getGenre(), "roman");
                Assert.assertEquals(book.getCondition(), "new");
            }
        }

        Assert.assertEquals(allBooksResponse.getErrors().size(), 0);

    }

}
