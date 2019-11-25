package apiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.AllBooksResponse;
import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;

import static io.restassured.RestAssured.given;

public class HomeworkGetAllBooks extends BaseTest {

    @Test
    public void getAllBooks_forEach() {

        Response response =
                given()
                        .baseUri(baseUrl)
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
                        .baseUri(baseUrl)
                        .basePath("api/books/all")
                        .headers(headers)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath json = response.jsonPath();

        var allBooksResponse = json.getObject("$", AllBooksResponse.class);

        Optional<Book> theBook = allBooksResponse.getValue().stream().filter(book->book.getId() == 126).findFirst();

        Assert.assertTrue(theBook.isPresent(),"Book not found");
        Assert.assertEquals(theBook.get().getLabel(), "Red Hood");
        Assert.assertEquals(theBook.get().getAuthor(), "Charles Peroh");
        Assert.assertEquals(theBook.get().getGenre(), "Fairytale");
        Assert.assertEquals(theBook.get().getCondition(), "new");

        Assert.assertEquals(allBooksResponse.getErrors().size(), 0);
    }
}