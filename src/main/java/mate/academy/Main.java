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
        book.setTitle("Lisova Mavka");
        book.setPrice(BigDecimal.valueOf(999));
        Book bookSaved = bookDao.create(book);
        System.out.println(bookDao.findById(bookSaved.getId()));
        Book secondBook = new Book();
        secondBook.setTitle("Detective Story");
        secondBook.setPrice(BigDecimal.valueOf(599));
        Book secondBookSaved = bookDao.create(secondBook);
        System.out.println(bookDao.findAll());
        secondBook.setTitle("Ironic Stories");
        System.out.println(bookDao.update(secondBookSaved));
        System.out.println(bookDao.deleteById(2l));
    }
}
