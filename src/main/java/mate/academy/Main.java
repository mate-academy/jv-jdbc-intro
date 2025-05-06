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

        Book lordOfTheRing = new Book();
        lordOfTheRing.setTitle("Lord of the ring");
        lordOfTheRing.setPrice(BigDecimal.valueOf(21.5));
        bookDao.create(lordOfTheRing);

        Book mainCamp = new Book();
        mainCamp.setTitle("Main camp");
        mainCamp.setPrice(BigDecimal.valueOf(20.0));
        bookDao.create(mainCamp);

        Optional<Book> bookById = bookDao.findById(1L);
        System.out.println(bookById);

        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks);

        Book updatedBook = bookDao.update(mainCamp);
        updatedBook.setPrice(BigDecimal.valueOf(50.5));
        System.out.println(updatedBook);

        bookDao.deleteById(1L);
    }
}
