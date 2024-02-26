package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setId(5L);
        book.setTitle("harry potter");
        book.setPrice(BigDecimal.valueOf(120));
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        bookDao.create(book);

        bookDao.findById(5L);

        List<Book> allBook = bookDao.findAll();
        System.out.println(allBook);

        book.setPrice(BigDecimal.valueOf(99));
        bookDao.update(book);

        bookDao.deleteById(1L);
    }
}
