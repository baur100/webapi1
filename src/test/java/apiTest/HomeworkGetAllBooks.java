package apiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.AllBooksResponse;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HomeworkGetAllBooks extends BaseTest {

    @Test
    public void getAllBooks_forEach() {

        Response response =
                given()
                        .baseUri(basUrl)
                        .basePath("api/books/all")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        System.out.println("======== List of All books: ============");
        json.prettyPrint();

        AllBooksResponse allBooksResponse = json.getObject("$", AllBooksResponse.class);
        var allBooks = allBooksResponse.getValue();

        //foreach

        for (Book book : allBooks) {
            if (book.getId() == 126) {
                Assert.assertEquals(book.getId(), 126);
                Assert.assertEquals(book.getLabel(), "Red Hood");
                Assert.assertEquals(book.getAuthor(), "Charles Peroh");
                Assert.assertEquals(book.getGenre(), "Fairytale");
                Assert.assertEquals(book.getCondition(), "new");
            }
        }

        Assert.assertEquals(allBooksResponse.getErrors().size(), 0);

    }

    @Test
    public void getAllBooks_streamApi() {

        Response response =
                given()
                        .baseUri(basUrl)
                        .basePath("api/books/all")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        System.out.println("======== List of All books: ============");
        json.prettyPrint();

        AllBooksResponse allBooksResponse = json.getObject("$", AllBooksResponse.class);
        var allBooks = allBooksResponse.getValue();

        allBooks.stream()
                .filter(book->book.getId() == 126)
                .forEach(book -> Assert.assertEquals(book.getLabel(), "Red Hood"));

        Assert.assertEquals(allBooksResponse.getErrors().size(), 0);
    }
}
