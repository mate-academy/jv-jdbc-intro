package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Mate");
        book.setPrice(BigDecimal.valueOf(500));
        bookDao.create(book);
        Book book2 = new Book();
        book2.setTitle("Academy");
        book2.setPrice(BigDecimal.valueOf(200));
        bookDao.create(book2);
        Book book3 = new Book();
        book3.setId(1L);
        book3.setTitle("Mate");
        book3.setPrice(BigDecimal.valueOf(400));
        bookDao.update(book3);
        Optional<Book> result = bookDao.findById(2L);
        System.out.println(result);
        bookDao.deleteById(1L);
        bookDao.findAll();
    }
}
