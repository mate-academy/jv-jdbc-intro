package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book("How to program in Java", BigDecimal.valueOf(10));

        try {
            bookDao.create(newBook);
            Optional<Book> book = bookDao.findById(newBook.getId());
            book.ifPresent(newBook1 -> {
                newBook1.setPrice(BigDecimal.valueOf(12));
                bookDao.update(newBook1);
            });
            bookDao.deleteById(newBook.getId());
        } catch (DataProcessingException e) {
            throw new RuntimeException("Data processing error occurred", e);
        }
    }
}
