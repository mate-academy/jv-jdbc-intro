package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final BigDecimal PRICE_1 = BigDecimal.valueOf(199.99);
    private static final BigDecimal PRICE_2 = BigDecimal.valueOf(159.99);
    private static final BigDecimal PRICE_3 = BigDecimal.valueOf(300);
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(99.99);
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("Crime and Punishment");
        book1.setPrice(PRICE_1);
        Book book2 = new Book();
        book2.setTitle("The Godfather");
        book2.setPrice(PRICE_2);
        Book book3 = new Book();
        book3.setTitle("Tractor");
        book3.setPrice(PRICE_3);

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);

        List<Book> bookList = bookDao.findAll();
        for (Book book : bookList) {
            if (book.getPrice().compareTo(PRICE_3) == 0) {
                book.setPrice(NEW_PRICE);
                bookDao.update(book);
            }
        }

        bookDao.deleteById(3L);
    }
}
