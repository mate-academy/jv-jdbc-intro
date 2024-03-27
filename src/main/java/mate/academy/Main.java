package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.dao");
    private static final int BOOK_PRICE = 200;
    private static final int NUMBER_OF_BOOKS = 3;
    private static final int NUMBER_OF_DELETED_BOOKS = 5;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        for (int i = 1; i <= NUMBER_OF_BOOKS; i++) {
            book.setTitle("Death Note. Book " + i);
            book.setPrice(BigDecimal.valueOf(BOOK_PRICE + i));
            bookDao.create(book);
        }
        System.out.println("Created books: " + bookDao.findAll());

        Optional<Book> bookById = bookDao.findById(5L);
        System.out.println("Book with index 5: " + bookById);

        book.setTitle("Hobbit");
        book.setPrice(BigDecimal.valueOf(300));
        Book updatedBook = bookDao.update(book);
        System.out.println("Updated book: " + updatedBook);

        for (int i = 1; i <= NUMBER_OF_DELETED_BOOKS; i++) {
            bookDao.deleteById((long) i);
        }
        System.out.println("Deleted first 5 books: " + bookDao.findAll());
    }
}
