package apiTest;

import models.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeworkDatabaseGetAllBooks {

    final private static String connectionUrl = "jdbc:sqlserver://aurorasqlserverdev.database.windows.net:1433;databaseName=lbrdev;user=aurorasqladmin;Password=mTDsj3BNe1J3!;sslProtocol=TLSv1";

//    https://stackoverflow.com/questions/11983554/reading-data-from-database-and-storing-in-array-list-object/47794668

    public static List<Book> getAllBooks() throws SQLException {
            List<Book> list = new ArrayList<>();

            Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();
            String query = "SELECT *  FROM [dbo].[Books];";
            ResultSet result = statement.executeQuery(query);



            while (result.next()){

                Book book = new Book();
                book.setId(result.getInt("Id"));
                book.setLabel(result.getString("Label"));
                book.setAuthor(result.getString("Author"));
                book.setGenre(result.getString("Genre"));
                book.setCondition(result.getString("Condition"));

                list.add(book);
            }

            return list;
        }

        @Test
        public void callToDBAllBooks() throws SQLException {
            var list = getAllBooks();
            Assert.assertTrue(list.size()>0);
        }

    public static List<Book> getAllBooksByAuthor(String author) throws SQLException {

            List<Book> list = new ArrayList<>();

            Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();
            String query = "SELECT *  FROM [dbo].[Books] where Author = '" + author + "';";
            ResultSet result = statement.executeQuery(query);



            while (result.next()){

                Book book = new Book();
                book.setId(result.getInt("Id"));
                book.setLabel(result.getString("Label"));
                book.setAuthor(result.getString("Author"));
                book.setGenre(result.getString("Genre"));
                book.setCondition(result.getString("Condition"));

                list.add(book);
            }
            return list;
        }

        @Test
        public void getAllBooksByAuthor_size() throws SQLException {
        String author = "Tanik";
        var list = getAllBooksByAuthor(author);
        Assert.assertTrue(list.size()>0);
        System.out.println("Found " + list.size() + " books by " + author);
        }

    }


