package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("Azbuka");
        firstBook.setPrice(new BigDecimal(100));
        Book secondBook = new Book();
        secondBook.setTitle("War and Peace");
        secondBook.setPrice(new BigDecimal(123));
        Book book = new Book();
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        book = bookDao.create(firstBook);
        bookDao.create(secondBook);
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());
        book.setTitle("Azbuka2");
        book.setPrice(new BigDecimal(222));
        bookDao.update(book);
        bookDao.deleteById(2L);
    }
}
