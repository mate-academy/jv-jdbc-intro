package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import mate.academy.dao.BookDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Random random = new Random();
    private static final int MIN_PRICE = 99;
    private static final int MAX_PRICE = 999;
    private static final List<String> bookTitle = new ArrayList<>(
            Arrays. asList(
            "Don Quixote",
            "Alice's Adventures in Wonderland",
            "The Adventures of Tom Sawyer",
            "Treasure Island",
            "Pride and Prejudice",
            "Wuthering Heights",
            "Jane Eyre",
            "Moby Dick",
            "The Hobbit, or, There and Back Again",
            "The Picture of Dorian Gray"
        ));

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();

        for (String title : bookTitle) {
            book.setTitle(title);
            book.setPrice(getPrice());
            bookDao.create(book);
        }

        List<Book> books = bookDao.findAll();
        System.out.println("All books => " + books);

        Optional<Book> firstBook = bookDao.findById(1L);
        System.out.println("First book => "
                + firstBook.orElseThrow(
                    () -> new DataProcessingException("Can't find a book by id")));

        Book bookForUpdate = new Book();
        bookForUpdate.setId(9L);
        bookForUpdate.setTitle("The Lord of the Rings");
        bookForUpdate.setPrice(getPrice());
        Book updatedBook = bookDao.update(bookForUpdate);
        System.out.println("Updated book => " + updatedBook);

        boolean isFirstBookDeleted = bookDao.deleteById(1L);
        System.out.println("Is first book deleted: " + isFirstBookDeleted);

        List<Book> booksAfterCrud = bookDao.findAll();
        System.out.println("All books => " + booksAfterCrud);
    }

    private static BigDecimal getPrice() {
        return BigDecimal.valueOf(random.nextInt(MAX_PRICE - MIN_PRICE) + MIN_PRICE);
    }
}
