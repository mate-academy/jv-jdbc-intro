package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book(1L, "Math", BigDecimal.valueOf(200));
        Book book2 = new Book(2L, "Biology", BigDecimal.valueOf(250));
        Book book3 = new Book(3L, "Chemistry", BigDecimal.valueOf(300));
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.deleteById(2L);
        List<Book> books = bookDao.findAll();
        Optional<Book> findBook = bookDao.findById(3L);
        System.out.println(books);
        System.out.println(findBook);
        Book book4 = new Book(3L, "Geography", BigDecimal.valueOf(280));
        Book updatedBook = bookDao.update(book4);
        System.out.println(updatedBook);
    }
}
