package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setPrice(BigDecimal.valueOf(231));

        Book book2 = new Book();
        book2.setTitle("I love Java");
        book2.setPrice(BigDecimal.valueOf(432));

        Book book3 = new Book();
        book3.setTitle("Widening our horizons");
        book3.setPrice(BigDecimal.valueOf(197));

        book1 = bookDao.create(book1);
        book2 = bookDao.create(book2);
        book3 = bookDao.create(book3);

        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        Book book4 = bookDao.findById(book1.getId()).get();

        book3.setTitle("Round up 5");
        book3.setPrice(BigDecimal.valueOf(560));
        book3 = bookDao.update(book3);

        bookDao.deleteById(book2.getId());
    }
}
