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
        Book book = new Book();
        book.setTitle("test");
        book.setPrice(BigDecimal.valueOf(210.67));
        bookDao.create(book);

        Optional<Book> bookById = bookDao.findById(book.getId());
        System.out.println(bookById.get());

        book.setTitle("update_test");
        bookDao.update(book);

        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks);

        bookDao.deleteById(book.getId());
        System.out.println(bookDao.findAll());
    }
}
