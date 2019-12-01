package apiTest;

import helpers.DbAdapter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.GetAllBooksResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class HomeWork6  extends BaseTest{

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
        var allBooksResponse = json.getObject("$", GetAllBooksResponse.class);

        Optional<Book> theBook = allBooksResponse.getValue().stream().filter(book->book.getId()==2).findFirst();

        Assert.assertTrue(theBook.isPresent(), "Book not found");

        Book book=theBook.get();
        Assert.assertEquals(book.getId(),2);
        Assert.assertEquals(book.getAuthor(),"Max Kamenni");
        Assert.assertEquals(book.getCondition(),"old");
        Assert.assertEquals(book.getGenre(),"utopia");
        Assert.assertEquals(book.getLabel(),"Mol Gennadi");
    }
    @Test
    public void callToDb() throws SQLException {
        var book = DbAdapter.getBookById(2);
        Assert.assertEquals(book.getId(),2);
    }

}

