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
        Book book1 = new Book("Math", BigDecimal.valueOf(200));
        Book book2 = new Book("Biology", BigDecimal.valueOf(250));
        Book book3 = new Book("Chemistry", BigDecimal.valueOf(300));
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.deleteById(book1.getId());
        List<Book> books = bookDao.findAll();
        Optional<Book> findBook = bookDao.findById(book3.getId());
        System.out.println(books);
        System.out.println(findBook);
        book3.setTitle("Geography");
        book3.setPrice(BigDecimal.valueOf(320));
        Book updatedBook = bookDao.update(book3);
        System.out.println(updatedBook);
    }
}
