package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Sapiens");
        book.setPrice(BigDecimal.valueOf(100));
        bookDao.create(book);
        Optional<Book> bookById = bookDao.findById(1L);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        System.out.println(bookById);
        book.setPrice(BigDecimal.valueOf(300));
        bookDao.update(book);
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(book.getId());
        bookDao.findAll().forEach(System.out::println);
    }
}
