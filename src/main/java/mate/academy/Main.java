package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Book book1 = new Book();
        book1.setTitle("1");
        book1.setPrice(BigDecimal.valueOf(1L));

        Book book2 = new Book();
        book2.setTitle("2");
        book2.setPrice(BigDecimal.valueOf(2L));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("Book created: " + bookDao.create(book1));
        System.out.println("Book created: " + bookDao.create(book2));

        book1.setPrice(BigDecimal.valueOf(49));
        System.out.println("Book updated: " + bookDao.update(book1));

        System.out.println("Found book1: " + bookDao.findById(3L));
        System.out.println("Found all books: " + bookDao.findAll());

        bookDao.deleteById(3L);
        System.out.println("Found all books after delete book1: " + bookDao.findAll());
    }
}
