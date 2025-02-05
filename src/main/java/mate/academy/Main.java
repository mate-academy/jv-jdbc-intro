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

        Book alchemist = new Book();
        alchemist.setTitle("The alchemist");
        alchemist.setPrice(BigDecimal.valueOf(8.61));

        bookDao.create(alchemist);

        Book wish = new Book();
        wish.setTitle("Wish");
        wish.setPrice(BigDecimal.valueOf(10.31));

        bookDao.create(wish);

        Book runner = new Book();
        runner.setTitle("The runner");
        runner.setPrice(BigDecimal.valueOf(11.48));

        bookDao.create(runner);

        Optional<Book> optionalBook = bookDao.findById(1L);
        System.out.println(optionalBook);

        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);

        bookDao.update(wish);

        System.out.println(bookDao.deleteById(3L));

    }
}
