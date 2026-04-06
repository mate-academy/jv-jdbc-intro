package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("Shantaram");
        book1.setPrice(BigDecimal.valueOf(200L));
        Book book2 = new Book();
        book2.setTitle("Altered carbon");
        book2.setPrice(BigDecimal.valueOf(150L));
        Book book3 = new Book();
        book3.setTitle("1984");
        book3.setPrice(BigDecimal.valueOf(160L));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        Book book12 = bookDao.findById(2L).get();
        System.out.println(book12);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        book1.setPrice(BigDecimal.valueOf(140));
        bookDao.updateBook(book1);
        bookDao.deleteById(1L);
    }
}
