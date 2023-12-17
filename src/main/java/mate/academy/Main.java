package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Stephen King - Holly", BigDecimal.valueOf(800));
        // Add book to DB
        bookDao.create(book);
        System.out.println("* Added new book");
        // Get book from DB
        Optional<Book> foundBook = bookDao.findById(book.getId());
        System.out.println(foundBook);
        System.out.println("* Updated book");
        // Update book in DB
        book.setPrice(BigDecimal.valueOf(900));
        bookDao.update(book);
        showDbState(bookDao);
        System.out.println("* Delete book");
        // Delete book from DB
        bookDao.deleteById(book.getId());
        showDbState(bookDao);
    }

    private static void showDbState(BookDao bookDao) {
        // Get all books from DB
        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks);
    }
}
