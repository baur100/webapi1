package helpers;

import models.Book;
import models.UsersBook;

import java.sql.*;
import java.util.*;

public class DbAdapter {
    final private static String connectionUrl = "jdbc:sqlserver://aurorasqlserverdev.database.windows.net:1433;databaseName=lbrdev;user=aurorasqladmin;Password=mTDsj3BNe1J3!;sslProtocol=TLSv1";

    public static Book getBookById(int id) throws SQLException {
        Book book = new Book();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT *  FROM [dbo].[Books] WHERE Id = " + id + ";";
        ResultSet result = statement.executeQuery(query);

        while (result.next()){
            book.setId(result.getInt("Id"));
            book.setLabel(result.getString("Label"));
            book.setAuthor(result.getString("Author"));
            book.setGenre(result.getString("Genre"));
            book.setCondition(result.getString("Condition"));
        }
        connection.close();
        return book;
    }

    public static List<Book> getBookByAuthor(String author) throws SQLException {
        List<Book> list = new ArrayList<Book>();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT *  FROM [dbo].[Books] WHERE Author = '" + author + "';";
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
        connection.close();
        return list;
    }

    public static List<Book> getAllBooks()throws SQLException{
        List<Book> list = new ArrayList<Book>();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT *  FROM [dbo].[Books];";
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

    public static int addBookToDb(Book book)throws SQLException{
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();

        var query = "INSERT INTO [dbo].[Books] VALUES ('"+
                book.getLabel()+"','"+book.getAuthor()+"','"+book.getGenre()+
                "','"+book.getCondition()+"');";
        statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
        ResultSet result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        connection.close();
        return id;
    }

    public static Boolean bookExistInDb(int id) throws SQLException{
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();

        String query = "SELECT * FROM [dbo].[Books] WHERE Id = "+id;
        ResultSet result=statement.executeQuery(query);
        return result.next();
    }

    public static void updateBookInDb(Book book)throws SQLException{
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "UPDATE [dbo].[Books] SET Label = '"+
                book.getLabel()+"', Author = '"+book.getAuthor()+"', Genre='"+
                book.getGenre()+"', Condition='"+book.getCondition()+"' WHERE id="+
                book.getId();
        statement.executeUpdate(query);
        connection.close();
    }

    public static void deleteBookFromDb(int id)throws SQLException{
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "DELETE FROM [dbo].[Books] WHERE Id="+id+";";
        statement.executeUpdate(query);
    }

    public static UsersBook getUsersBookById(int id) throws SQLException {
        UsersBook usersBook = new UsersBook();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT *  FROM [dbo].[UserBooks] WHERE Id = " + id + ";";
        ResultSet result = statement.executeQuery(query);

        while (result.next()){
            usersBook.setBookId(result.getInt("BookId"));
            usersBook.setId(result.getInt("Id"));
            usersBook.setUserId(result.getString("UserId"));
        }
        connection.close();
        return usersBook;
    }
    public static List<UsersBook> getAllUsersBooks()throws SQLException{
        List<UsersBook> list = new ArrayList<UsersBook>();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT *  FROM [dbo].[UserBooks];";
        ResultSet result = statement.executeQuery(query);

        while (result.next()){
            var usersBook = new UsersBook();
            usersBook.setBookId(result.getInt("BookId"));
            usersBook.setId(result.getInt("Id"));
            usersBook.setUserId(result.getString("UserId"));
            list.add(usersBook);
        }
        connection.close();
        return list;
    }
    public static void deleteUsersBookFromDb(int id)throws SQLException{
        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "DELETE FROM [dbo].[UserBooks] WHERE Id="+id+";";
        statement.executeUpdate(query);
    }

    public static List<Integer> getUnusedBookIds() throws SQLException {
        List<Integer> list = new ArrayList<>();

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement();
        String query = "SELECT a.id FROM Books as a FULL JOIN UserBooks as b ON a.Id = b.BookId WHERE a.Id IS NULL OR b.BookId IS NULL;";
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            int id;
            id = result.getInt("Id");
            list.add(id);
        }
        connection.close();
        return list;
    }

}
