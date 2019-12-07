package models;

import java.util.UUID;

public class UsersBook {
    private UUID userId;
    private int bookId;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public UUID getUserId() {
        return userId;
    }
}
