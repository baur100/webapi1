package apiTest;

import helpers.DbAdapter;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import models.Book;
import models.BookResponse;
import models.GetAllBooksResponse;
import org.apache.http.client.utils.URIBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;


import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class GetTest extends BaseTest{

    @Test
            public void testGet_getABookById() {

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
        json.prettyPrint();

        //we can retrive only part of response

        Book book = json.getObject("value",Book.class);
        Assert.assertEquals(book.getId(),240);
        Assert.assertEquals(book.getAuthor(),"XXstring");
        Assert.assertEquals(book.getCondition(),"XXstring");
        Assert.assertEquals(book.getGenre(),"XXstring");
        Assert.assertEquals(book.getLabel(),"XXstring");




        BookResponse bookResponse = json.getObject("$",BookResponse.class);

        //Assertion that array Errors is empty

        Assert.assertEquals(bookResponse.getErrors().size(),0);

        //assert that if of retrived book is 240
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


        var getAllBooksRespons = json.getObject("$",GetAllBooksResponse.class);

        Assert.assertEquals(getAllBooksRespons.getErrors().size(),0);

        // Book theBook = new Book();
       Optional<Book> theBook = getAllBooksRespons.getValue().stream().filter(book->book.getId()==30).findFirst();


//        for (Book book:getAllBooksRespons.getValue()){
//            if(book.getId()==30){
//                theBook = book;
//
//            }
//        }

       Assert.assertTrue(theBook.isPresent(),"Book not found");
       Book book =theBook.get();
        Assert.assertEquals(book.getId(),30);
        Assert.assertEquals(book.getAuthor(),"Joan Rouling");
        Assert.assertEquals(book.getCondition(),"new");
        Assert.assertEquals(book.getGenre(),"fantasy");
        Assert.assertEquals(book.getLabel(),"Harry Potter");


    }
    @Test
    public  void callToDb() throws SQLException {
        var book = DbAdapter.getBookById(30);
        Assert.assertEquals(book.getId(),30);
    }

    @Test
    public void getAllBooksFromDb() throws SQLException {
        var list =DbAdapter.getAllBooks();
       Assert.assertTrue(list.size()!=0);
    }
    @Test
    public void addBookToDb() throws SQLException {
        var book = new Book(0,"newbook", "Baur","Programming","new");
        int id = DbAdapter.addBookToDb(book);
        Assert.assertTrue(id>0);

    }
    @Test
    public void upDateBookToDb() throws SQLException {
        var book = new Book(333, "newbook", "Baur", "Programming", "new");
        DbAdapter.updateBookInDb(book);
    }
    @Test
    public void deleteBookFromDb() throws SQLException {
        DbAdapter.deleteBookFromDb(336);
    }
    @Test
    public void getAllBooksByAuthor() throws SQLException {
        var list = DbAdapter.selectAllBooksByAuthor("Baur1");
//       Assert.assertTrue(list.size()!=0);
    }




}
