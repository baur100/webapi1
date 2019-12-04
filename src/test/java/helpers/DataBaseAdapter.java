package helpers;

import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter {
    final private static String connectionUrl = "jdbc:sqlserver://aurorasqlserverdev.database.windows.net:1433;databaseName=lbrdev;user=aurorasqladmin;Password=mTDsj3BNe1J3!;sslProtocol=TLSv1";

    public static Book getBookById(int id) throws SQLException {
        Book book = new Book();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
//        var statement = DriverManager.getConnection(connectionUrl).createStatement();
        String query = "SELECT * FROM [dbo].[Books] WHERE Id = " + id + ";";
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            book.setId(result.getInt("Id"));
            book.setLabel(result.getString("Label"));
            book.setAuthor(result.getString("Author"));
            book.setGenre(result.getString("Genre"));
            book.setCondition(result.getString("Condition"));
        }

        connection.close();

        return book;
    }


    public static List<Book> getAllBooks() throws SQLException {
        List<Book> list = new ArrayList<>();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
//        var statement = DriverManager.getConnection(connectionUrl).createStatement();
        String query = "SELECT * FROM [dbo].[Books];";
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
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

    public static int addBookToDataBase(Book book) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();

        var query = "INSERT INTO [dbo].[Books] VALUES ('" + book.getLabel() + "','" + book.getAuthor() + "','" + book.getGenre() +
                "','" + book.getCondition() + "');";
        statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        connection.close();
        return id;
    }

    public static void updateBookInDataBase(Book book) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "UPDATE [dbo].[Books] SET Label = '" +
                book.getLabel() + "', Author = '" + book.getAuthor() + "', Genre = '"
                + book.getGenre() + "', Condition = '" + book.getCondition() + "' WHERE Id = " + book.getId() + ";";
        statement.executeUpdate(query);
        connection.close();
    }

    public static void deleteBookFromDataBase(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "DELETE FROM [dbo].[Books] WHERE Id = " + id + ";";
        statement.executeUpdate(query);
    }

    public static List<Book> getAllBooksByAuthorFromDataBase() throws SQLException {
        List<Book> list = new ArrayList<>();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM [dbo].[Books] WHERE Author LIKE 'S%' ORDER BY Author ASC;";
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
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
