package helpers;

import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAdaptor {
    final private static String connectionUrl = "jdbc:sqlserver://aurorasqlserverdev.database.windows.net:1433;databaseName=lbrddev;user=aurora";

    public static Book getBookById(int id) throws SQLException {
       Book book=new Book();

        Connection connection= DriverManager.getConnection(connectionUrl);
        Statement statement= connection.createStatement();
        String query = "SELECT *  FROM [dbo].[Books] WHERE id = " +id + ";";
        ResultSet result = statement.executeQuery(query);
        while (result.next()){
            book.setId(result.getInt("id"));
            book.setLabel(result.getString("Label"));
            book.setAuthor(result.getString("Author"));
            book.setGenre(result.getString("Genre"));
            book.setCondition(result.getString("Condition"));
        }
        connection.close();
        return book;
    }
    public static List<Book> getAllBooksByAuthor(String author) throws SQLException {
        List<Book> list = new ArrayList<Book>();
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query= "SELECT * FROM Books WHERE Author LIKE '"+ author + "');";
        ResultSet result = statement.executeQuery(query);
        while (result.next()){
            var book = new Book();
            book.setId(result.getInt("Id"));
            book.setLabel(result.getString("Label"));
            book.setAuthor(result.getString("Author"));
            book.setGenre(result.getString("Genre"));
            book.setCondition(result.getString("Condition"));
            list.add(book);
        }
        connection.close();
        return list;
    }
}
