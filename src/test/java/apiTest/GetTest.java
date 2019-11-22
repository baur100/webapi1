package apiTest;

import io.restassured.path.json.JsonPath;
import models.AllBooksResponse;
import models.Book;
import models.BookResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.net.URISyntaxException;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class GetTest extends BaseTest{


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
                    .baseUri(basUrl)
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

        BookResponse bookResponse = json.getObject("$",BookResponse.class);
        Book book=json.getObject("value",Book.class);
        Assert.assertEquals(book.getId(),240);
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

    public void getAllBooks(){
        Response response=
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

        JsonPath json=response.jsonPath();
        json.prettyPrint();

        AllBooksResponse allBooksResponse=json.getObject("$",AllBooksResponse.class);
        var book=allBooksResponse.getValue();

        //Using foreach
        for (Book allBooks : book) {
            if (allBooks.getId()==2){
                Assert.assertEquals(allBooks.getId(), 2);
                Assert.assertEquals(allBooks.getLabel(), "Mol Gennadi");
                Assert.assertEquals(allBooks.getAuthor(), "Max Kamenni");
                Assert.assertEquals(allBooks.getGenre(), "utopia");
                Assert.assertEquals(allBooks.getCondition(), "old");
            }
        }
        //Using stream API
        book.stream()
                .filter(allBooks->allBooks.getId()==2)
        .forEach(allBooks->Assert.assertEquals(allBooks.getLabel(), "Mol Gennadi"));

//            Assert.assertEquals(allBooks.getLabel(), "Mol Gennadi");
//            Assert.assertEquals(allBooks.getAuthor(), "Max Kamenni");
//            Assert.assertEquals(allBooks.getGenre(), "utopia");
//            Assert.assertEquals(allBooks.getCondition(), "old");

    }
}
