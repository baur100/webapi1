package apiTest;

import com.google.gson.Gson;
import helpers.DataGenerator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookCreateResponse;
import models.BookDeleteResponse;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static helpers.DataGenerator.generateRandomBook;
import static io.restassured.RestAssured.given;


public class CreateUpdateDeleteTest extends BaseTest {
    private Book book;

    public  void init(){
        book = DataGenerator.generateRandomBook();
        String payload = new Gson().toJson(book);

        Response response =
                given().baseUri(baseUrl).basePath("api/books").headers(headers).body(payload)
                        .post().then().extract().response();
        var id = response.jsonPath().getObject("$", BookCreateResponse.class).getvalue();
        book.setId(id);


    }
    @AfterMethod
    public void tearDown(){
        given().baseUri(baseUrl).basePath("api/books/id/"+book.getId()).headers(headers)
                        .delete();


    }


    @Test
    public void createBook() {

        //Arrange

        book = generateRandomBook();

        // book is json and payload - is String; with help of Gson Json=>String
        String payload = new Gson().toJson(book);

        //Act

        Response response =
                given()
                    .baseUri(baseUrl)
                    .basePath("api/books")
                    .headers(headers)
                    .body(payload)
                .when()
                    .post()
                .then()
                    .extract()
                     .response();
        //Assert

//responce come on escape format and we need transfeorm it to java class
        Assert.assertEquals(response.statusCode(),200);
        JsonPath json = response.jsonPath();
        var bookCreateResponse = json.getObject("$", BookCreateResponse.class);
        Assert.assertEquals(bookCreateResponse.getErrors().size(),0);
        Assert.assertTrue(bookCreateResponse.getvalue()>0);
        //в следующей строчке присваиваем книге Id только что созданной книги
        // что бы потом в афтерметоде удалить книку с этим айди
        book.setId(bookCreateResponse.getvalue());

          }


    @Test
    public void deleteBook(){
        init();
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/"+book.getId())
                        .headers(headers)
                        .when()
                        .delete()
                        .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.statusCode(),200);
        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().size(),0);

        Assert.assertNotNull(bookDeleteResponse.value);
        //now we assert that value is not NULL, because if it s will be null - we will have an exception
        //now we make value and not getValue, because it s boolean and it s public
        Assert.assertTrue(bookDeleteResponse.value);

    }
    @Test
    public void deleteNotExistedBook(){
        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/253")
                        .headers(headers)
                .when()
                        .delete()
                .then()
                        .extract()
                        .response();

        Assert.assertEquals(response.statusCode(),404);
        JsonPath json = response.jsonPath();
        var bookDeleteResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookDeleteResponse.getErrors().get(0),"The book record couldn't be found.");
        Assert.assertFalse(bookDeleteResponse.value);

    }

    @Test

    public void upDateBook(){
        init();
        //Arrange
//создаем другую книгу что бы потом ее параментрами заменять параметры первой книги
       // Book book1 = generateRandomBook();

        //this if we would like update only one field on book - codition

        Book book1 = new Book(book.getId(),book.getLabel(),book.getAuthor(),book.getGenre(),"newwwww");
        //book is Java object and we transform it to Json for make a call and store it to the String payload
        // Java=>Json (String)

        String payload = new Gson().toJson(book1);

        //Act

        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/books/id/"+book.getId())
                        .headers(headers)
                        .body(payload)
                .when()
                        .put()
                .then()
                        .extract()
                        .response();
        //Assert

        Assert.assertEquals(response.statusCode(),200);
        JsonPath json = response.jsonPath();
        var bookPutResponse = json.getObject("$", BookDeleteResponse.class);
        Assert.assertEquals(bookPutResponse.getErrors().size(),0);
        Assert.assertTrue(bookPutResponse.value);
        Assert.assertNotNull(bookPutResponse.value);

    }
    @Test
    private void getBookById(){
        init();
       Response response =
                    given()
                            .baseUri(baseUrl)
                            .basePath("api/books/id/"+book.getId())
                            .headers(headers)
                            .when()
                            .get()
                            .then()
                            .extract()
                            .response();

            Assert.assertEquals(response.getStatusCode(),200);

            JsonPath json = response.jsonPath();
            BookResponse bookResponse = json.getObject("$",BookResponse.class);
            Assert.assertEquals(bookResponse.getValue().getLabel(),book.getLabel());
            Assert.assertEquals(bookResponse.getValue().getGenre(),book.getGenre());
            Assert.assertEquals(bookResponse.getValue().getAuthor(),book.getAuthor());
            Assert.assertEquals(bookResponse.getValue().getCondition(),book.getCondition());

    }


    }
