package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String SHINING = "Shining";
    private static final double SHINING_PRICE = 120.00;
    private static final String CARNIVAL = "Carnival";
    private static final double CARNIVAL_PRICE = 80.00;
    private static final String DARK_TOWER = "Dark tower";
    private static final double DARK_TOWER_PRICE = 100.00;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // initialize field values using setters or constructor
        Book shining = Book.builder()
                .title(SHINING)
                .price(BigDecimal.valueOf(SHINING_PRICE))
                .build();
        Book carnival = Book.builder()
                .title(CARNIVAL)
                .price(BigDecimal.valueOf(CARNIVAL_PRICE))
                .build();
        Book darkTower = Book.builder()
                .title(DARK_TOWER)
                .price(BigDecimal.valueOf(DARK_TOWER_PRICE))
                .build();
        Book storedShining = bookDao.create(shining);
        bookDao.create(carnival);
        bookDao.findById(storedShining.getId());
        darkTower.setId(storedShining.getId());
        bookDao.update(darkTower);
        List<Book> books = bookDao.findAll();
        books.forEach(book -> bookDao.deleteById(book.getId()));
        // test other methods from BookDao
    }
}
