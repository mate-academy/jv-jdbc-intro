package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(new Book("First Book", BigDecimal.valueOf(45)),
                new Book("Second Book", BigDecimal.valueOf(12)),
                new Book("Third Book", BigDecimal.valueOf(26)));
        for (Book book : books) {
            bookDao.create(book);
        }
        bookDao.findById(1L);
        System.out.println(bookDao.findAll());
        Book book = new Book();
        book.setId(43L);
        book.setTitle("Update First Book");
        book.setPrice(BigDecimal.valueOf(45));
        bookDao.update(book);
        bookDao.deleteById(43L);
    }
}
