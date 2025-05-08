package mate.academy.service;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("DeadSpace");
        book.setPrice(new BigDecimal(300));

        bookDao.create(book);
        System.out.println(bookDao.findById(1L));

        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setTitle("DEADSPACE");
        updateBook.setPrice(new BigDecimal(400));

        bookDao.update(updateBook);
        System.out.println(bookDao.findById(1L));

        bookDao.create(book);
        bookDao.deleteById(2L);
        System.out.println(bookDao.findAll());
    }
}
