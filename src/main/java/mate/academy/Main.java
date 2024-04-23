package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("Harry Potter and the Philosopher's / Sorcerer's Stone");
        firstBook.setPrice(BigDecimal.valueOf(200.56));
        Book secondBook = new Book();
        secondBook.setTitle("Harry Potter and the Chamber of Secrets");
        secondBook.setPrice(BigDecimal.valueOf(124.55));
        Book thirdBook = new Book();
        thirdBook.setTitle("Harry Potter and the Order of the Phoenix");
        thirdBook.setPrice(BigDecimal.valueOf(99.55));
        Book forthBook = new Book();
        forthBook.setTitle("Harry Potter and the Half-Blood Prince");
        forthBook.setPrice(BigDecimal.valueOf(245.09));
        Book fifthBook = new Book();
        fifthBook.setTitle("Harry Potter and the Deathly Hallows");
        fifthBook.setPrice(BigDecimal.valueOf(274.99));
        Book sixthBook = new Book();
        sixthBook.setId(4L);
        sixthBook.setTitle("Harry Potter and the Prisoner of Azkaban");
        sixthBook.setPrice(BigDecimal.valueOf(134.90));
        bookDao.create(fifthBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);
        bookDao.create(forthBook);
        bookDao.create(fifthBook);
        bookDao.update(sixthBook);
        bookDao.deleteById(3L);
        System.out.println(bookDao.findById(5L));
        bookDao.findAll().forEach(System.out::println);
    }
}
