package models;

import java.util.UUID;

public class UsersBook {
    private UUID userId;
    private int bookId;
    private int id;

    public int getBookId() {
        return bookId;
    }

    public int getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = UUID.fromString(userId);
    }
}
