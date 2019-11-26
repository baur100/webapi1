package helpers;

import models.Book;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {
    public static Book generateRandomBook() {
        Book book = new Book();

        book.setAuthor(RandomStringUtils.random(8, true, false));
        book.setCondition(RandomStringUtils.random(5, true, false));
        book.setGenre(RandomStringUtils.random(6, true, false));
        book.setLabel(RandomStringUtils.random(12, true, false));

        return book;
    }
}
