package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao.impl");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("7 Countries");
        book.setPrice(BigDecimal.valueOf(853));

        Book newBook = bookDao.create(book);

        boolean delResult = bookDao.deleteById(1L);

        book.setId(2L);
        Book updateBook = bookDao.update(book);

        Optional<Book> bookById = bookDao.findById(3L);

        List<Book> allBooks = bookDao.findAll();
    }
}
