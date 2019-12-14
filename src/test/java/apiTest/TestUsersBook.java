package apiTest;

import com.google.gson.Gson;
import com.sun.nio.sctp.PeerAddressChangeNotification;
import helpers.DbAdapter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Book;
import models.BookDeleteResponse;
import models.UsersBook;
import models.UsersBookAddResponse;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class TestUsersBook extends BaseTest{
    int bookId;
    int idUsersBook;
    private final String userId = "aa22b9b2-9160-432b-91c1-b2c6466d8dfb";

    @BeforeMethod
    public void startUp() throws SQLException {

        List<Book> books = DbAdapter.getAllBooks();
        //вытаскиеваем только ID  у каждой книги из списка и делаем список из ID
        List<Integer> bookIds = books.stream().map(Book::getId).collect((Collectors.toList()));

        List<UsersBook> ubList = DbAdapter.getAllUsersBooks();
        //создаем пустой аррай куда зносим все книги что нет в списке где их взял какой то юзер
        List<Integer>allowedBooks = new ArrayList<Integer>();
        for(int id:bookIds){
            //проверяем что бы ид не совпадали и потом заносим не повротяющееся ид в список
            if(ubList.stream().noneMatch(x->x.getBookId()==id))
                allowedBooks.add(id);
        }
        var random = new Random();
        //достаем из предыдущего аррея случайнуй ИД с случайным индексом
        var bookId=allowedBooks.get(random.nextInt(allowedBooks.size()));
        //достаем саму книгу с выбранным ИД из БД
      //  book = DbAdapter.getBookById(bookId);
        //второй вариант доставания книга по этому ИД
        //все книги превратить в потокб и потом фильтруем и выбираем из потока книгу с нужным ид
        // но в результате все расно у нас получается коллекция и мы просим только первое значение

       // book = books.stream().filter(x->x.getId()==bookId() .findFirst().get();



        }
        @AfterMethod
        public void tearDown() throws SQLException {
        DbAdapter.deleteUsersBookFromDb(idUsersBook);
        }



    @Test
    public void addBookToUser() throws SQLException {

        var usersBook = new UsersBook();
        usersBook.setUserId(userId);
        usersBook.setBookId(bookId);
        String payload = new Gson().toJson(usersBook);

        //Act

        Response response =
                given()
                        .baseUri(baseUrl)
                        .basePath("api/users/books/access/add")
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
        var ubResponse = json.getObject("$", UsersBookAddResponse.class);

        Assert.assertEquals(ubResponse.getErrors().size(),0);
        Assert.assertNotNull(ubResponse.getValue()>0);
        //вытаскиеваем Ид транзакции
        idUsersBook = ubResponse.getValue();
        //вытащили строку заиса из ДБ про юзеров с книгами
        var usersBookFromDb= DbAdapter.getUsersBookById(idUsersBook);
        Assert.assertEquals(usersBookFromDb.getBookId(),bookId);
        Assert.assertEquals(usersBookFromDb.getUserId().toString(),userId);





    }
}
