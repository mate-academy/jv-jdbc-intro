package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookForUpdating = new Book("Kobzar", new BigDecimal("346"));
        bookDao.create(bookForUpdating);
        bookDao.create(new Book("Bible", new BigDecimal("463")));
        bookDao.create(new Book("Coran", new BigDecimal("643")));
        List<Book> booksFirstShelf = bookDao.findAll();
        booksFirstShelf.forEach(System.out::println);
        bookForUpdating.setTitle("Tripiṭaka");
        bookForUpdating.setPrice(new BigDecimal("435"));
        bookDao.update(bookForUpdating);
        //Optional<Book> optionalBook = bookDao.findById(10L);
        Optional<Book> optionalBook = bookDao.findById(bookForUpdating.getId());
        optionalBook.ifPresent(System.out::println);
        bookDao.deleteById(bookForUpdating.getId());
        List<Book> booksSecondShelf = bookDao.findAll();
        booksSecondShelf.forEach(System.out::println);
    }
}
