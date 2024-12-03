package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book("Criminal", BigDecimal.valueOf(100));
        Book secondBook = new Book("Good man", BigDecimal.valueOf(110));
        Book savedBook = bookDao.create(firstBook);
        bookDao.create(secondBook);
        System.out.println("Book was added to database: " + savedBook);

        Book updateBook = bookDao.findById(savedBook.getId()).orElseThrow(() ->
                new DataProcessingException("Book was not found"));
        updateBook.setPrice(BigDecimal.valueOf(150));
        updateBook.setTitle("Updated Criminal");
        bookDao.update(updateBook);

        System.out.println("All books in the database:");
        bookDao.findAll().forEach(System.out::println);

        if (bookDao.deleteById(updateBook.getId())) {
            System.out.println("Book was deleted");
        }
    }
}
