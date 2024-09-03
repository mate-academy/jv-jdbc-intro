package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(new Book("MyBook1", BigDecimal.valueOf(60)),
                new Book("MyBook2", BigDecimal.valueOf(14)),
                new Book("MyBook3", BigDecimal.valueOf(51)));
        for (Book book : books) {
            bookDao.create(book);
        }
        bookDao.findById(1L);
        bookDao.findAll();
        Book book = new Book("MyBook4Update", BigDecimal.valueOf(19));
        book.setId(1L);
        bookDao.update(book);
        bookDao.deleteById(1L);
    }
}
