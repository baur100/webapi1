package apiTest;

import helpers.DBAdaptor;
import io.restassured.path.json.JsonPath;
import models.GetAllBooksResponse;
import models.Book;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class MyGetTest extends BaseTest {
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
                        .baseUri(baseUrl)
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

    @Test

    public void getAllBooks() {
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
        json.prettyPrint();

        var allBooksResponse = json.getObject("$", GetAllBooksResponse.class);

        //Using foreach
//                Book theBook = new Book();
//        var books = allBooksResponse.getValue();

//        for (Book book : books) {
//            if (book.getId() == 2) {
//                theBook = book;
//            }
//        }
//                Assert.assertEquals(theBook.getId(), 2);
//                Assert.assertEquals(theBook.getLabel(), "Mol Gennadi");
//                Assert.assertEquals(theBook.getAuthor(), "Max Kamenni");
//                Assert.assertEquals(theBook.getGenre(), "utopia");
//                Assert.assertEquals(theBook.getCondition(), "old");
//                Assert.assertEquals(allBooksResponse.getErrors().size(), 0);

        //Using stream API
        Optional<Book> theBook = allBooksResponse.getValue().stream().filter(book -> book.getId() == 2).findFirst();

        Assert.assertTrue(theBook.isPresent(), "Book not found");

        Book book = theBook.get();
        Assert.assertEquals(book.getId(), 2);
        Assert.assertEquals(book.getAuthor(), "Max Kamenni");
        Assert.assertEquals(book.getCondition(), "old");
        Assert.assertEquals(book.getGenre(), "utopia");
        Assert.assertEquals(book.getLabel(), "Mol Gennadi");
        Assert.assertEquals(allBooksResponse.getErrors().size(), 0);

    }
    @Test
    public void getAllBooksByAuthor() throws SQLException {
        var list= DBAdaptor.getAllBooksByAuthor("%Tolstoy");
        System.out.println(list);
        Assert.assertTrue(list.size()>0);
    }

}
