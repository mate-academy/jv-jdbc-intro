package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final String MAIN_PACKAGE_NAME = "mate.academy";
    private static final Injector injector;
    private static final BookDao bookDao;
    private static final Book book;

    static {
        injector = Injector.getInstance(MAIN_PACKAGE_NAME);
        bookDao = (BookDao) injector.getInstance(BookDao.class);
        book = new Book();
        book.setTitle("first title");
        book.setPrice(BigDecimal.valueOf(100));
    }

    public static void main(String[] args) {
        Book addedBook = bookDao.create(book);
        System.out.println("Book added: " + addedBook);
        Optional<Book> foundBook = bookDao.findById(addedBook.getId());
        System.out.println("Found book: " + foundBook.orElse(new Book()));
        Book updatedBook = bookDao.update(foundBook.orElse(new Book()));
        System.out.println("Book updated: " + updatedBook);
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in DB" + allBooks);
        boolean isDeleted = bookDao.deleteById(addedBook.getId());
        if (isDeleted) {
            System.out.println("Book deleted:" + addedBook);
        }
    }
}
