package mate.academy;

import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Boundaries",BigDecimal.valueOf(25));
        Book newBook = new Book("English in use",BigDecimal.valueOf(15));
        Book addBook = bookDao.create(book);
        bookDao.create(newBook);
        Optional<Book> findBookById = bookDao.findById(addBook.getId());
        List<Book> allBooks = bookDao.findAll();
        Book updateBook = bookDao.update(book);
        bookDao.deleteById(newBook.getId());
    }
}
