package mate.academy;

import java.math.BigDecimal;
import java.util.Random;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        for (long i = 1L; i <= 4L; i++) {
            Book book = new Book(i, "Book " + i,
                    BigDecimal.valueOf(5 + new Random().nextDouble() * 100));
            bookDao.create(book);
        }
        System.out.println("Book with ID 1: " + bookDao.findById(1L));

        BigDecimal newPrice = BigDecimal.valueOf(5 + new Random().nextDouble() * 100);
        Book updatedBook = new Book(2L, "New title", newPrice);
        bookDao.update(updatedBook);
        System.out.println("Updated book list: " + bookDao.findAll());

        bookDao.deleteById(3L);
        System.out.println("Book list after deletion: " + bookDao.findAll());
    }
}
