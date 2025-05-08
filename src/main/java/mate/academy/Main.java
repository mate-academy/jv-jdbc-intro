package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Dune", BigDecimal.valueOf(450L));
        Book book2 = new Book("Flower for algernon", BigDecimal.valueOf(116L));

        bookDao.create(book1);
        bookDao.create(book2);

        System.out.println(bookDao.findById(book1.getId()).get());
        System.out.println(bookDao.findById(book2.getId()).get());

        book1.setPrice(BigDecimal.valueOf(500L));
        bookDao.update(book1);
        book2.setPrice(BigDecimal.valueOf(140L));
        bookDao.update(book2);

        System.out.println(bookDao.findAll().toString());
        bookDao.deleteById(book1.getId());
    }
}
