package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("The two device");
        book.setPrice(BigDecimal.valueOf(70));
        bookDao.create(book);

        System.out.println(bookDao.findById(1L));

        System.out.println(bookDao.findAll());

        Book newBook = new Book();
        newBook.setId(2L);
        newBook.setTitle("The two device");
        newBook.setPrice(BigDecimal.valueOf(99));
        System.out.println(bookDao.update(newBook));

        System.out.println(bookDao.deleteById(1L));
    }
}
