package apiTest;

import helpers.DbAdapter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookResponse;
import models.GetAllBooksResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class GetTest extends BaseTest {
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

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath json = response.jsonPath();
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
                        .extract()
                        .response();

        Assert.assertEquals(response.getStatusCode(), 200);

        JsonPath json = response.jsonPath();
        var allBooksResponse = json.getObject("$", GetAllBooksResponse.class);

        Optional<Book> theBook = allBooksResponse.getValue().stream().filter(book -> book.getId() == 30).findFirst();

        Assert.assertTrue(theBook.isPresent(), "Book not found");

        Book book = theBook.get();
        Assert.assertEquals(book.getId(), 30);
        Assert.assertEquals(book.getAuthor(), "Joan Rouling");
        Assert.assertEquals(book.getCondition(), "new");
        Assert.assertEquals(book.getGenre(), "fantasy");
        Assert.assertEquals(book.getLabel(), "Harry Potter");
    }

    @Test
    public void callToDb() throws SQLException {
        var book = DbAdapter.getBookById(30);
        Assert.assertEquals(book.getId(), 30);
    }

    @Test
    public void getAllBooksFromDb() throws SQLException {
        var list=DbAdapter.getAllBooks();
        Assert.assertTrue(list.size()>0);
    }
    @Test
    public void addBookToDb() throws SQLException {
        var book = new Book(0,"newBook","Baur","Programming","new");
        int id = DbAdapter.addBookToDb(book);
        Assert.assertTrue(id>0);
    }
    @Test
    public void updateBookToDb() throws SQLException {
        var book = new Book(550,"newBook","Baur","Programming","new");
        DbAdapter.updateBookInDb(book);
    }
    @Test
    public void deleteBookToDb() throws SQLException {
        DbAdapter.deleteBookFromDb(335);
    }
    @Test
    public void getByAuthor() throws SQLException {
        var books =DbAdapter.getBookByAuthor("Max Kamenni");
        Assert.assertTrue(books.size()>0);
    }
    @Test
    public void getUsersBookById() throws SQLException {
        var usersBook = DbAdapter.getUsersBookById(22);
        Assert.assertEquals(usersBook.getBookId(),31);
    }
    @Test
    public void getAllUsersBook() throws SQLException{
        var list = DbAdapter.getAllUsersBooks();
        Assert.assertTrue(list.size()>0);
    }

}
