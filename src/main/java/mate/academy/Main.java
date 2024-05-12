package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

private static final Injector injector = Injector.getInstance("mate.academy");
public class Main {
    public static void main(String[] args) {
        BookDaoImpl bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Frodo and stuff");
        book.setPrice(new BigDecimal(100));
        bookDao.create(book);

        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresent(System.out::println);

        book.setTitle("Eldery stuff");
        bookDao.update(book);

        bookDao.deleteById(1L);

        bookDao.findAll().forEach(System.out::println);
    }
}

