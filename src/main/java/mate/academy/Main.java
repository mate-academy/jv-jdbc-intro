package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book(null, "The lost name", new BigDecimal(30));
        bookDao.create(book1);
        Book book2 = new Book(1L, "The lost name 2", new BigDecimal(35));
        bookDao.update(book2);
        Optional<Book> book3 = bookDao.findById(1L);
        List<Book> bookList = bookDao.findAll();
        boolean isDeletedBook = bookDao.deleteById(1L);
    }
}
