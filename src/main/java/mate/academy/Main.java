package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book harryPotterBook = new Book("Harry Potter", new BigDecimal("10.99"));
        Book duneBook = new Book("Dune", new BigDecimal("11.99"));

        bookDao.create(harryPotterBook);
        bookDao.create(duneBook);

        Book harryPotterActual = bookDao.findById(1L).orElse(null);
        Book duneActual = bookDao.findById(2L).orElse(null);
        if (harryPotterActual == null || duneActual == null) {
            System.out.println("Something went wrong");
            return;
        }

        List<Book> books = bookDao.findAll();

        Book newDunePriceBook =
                new Book(duneActual.getId(), duneActual.getTitle(), new BigDecimal("20.99"));
        bookDao.update(newDunePriceBook);

        bookDao.deleteById(1L);

    }
}
