package helpers;

import models.Book;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {
    public static Book generateRandomBook() {
        var randomBook = new Book();

        randomBook.setAuthor("NewRandomAuthor" + RandomStringUtils.random(4, false, true));
        randomBook.setGenre("NewRandomGenre" + RandomStringUtils.random(4, false, true));
        randomBook.setLabel("NewRandomLabel" + RandomStringUtils.random(4, false, true));
        randomBook.setCondition("NewRandomCondition" + RandomStringUtils.random(4, false, true));

        return randomBook;
    }
}
