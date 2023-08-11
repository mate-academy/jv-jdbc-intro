package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Interview with a developer");
        book.setPrice(BigDecimal.valueOf(7.20));
        Book bookFromDao = bookDao.create(book);
        System.out.println(bookFromDao);

        Book bookFindById = bookDao.findById(2L).orElse(null);
        System.out.println(bookFindById);

        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);

        if (bookFindById != null) {
            bookFindById.setPrice(BigDecimal.valueOf(2000000));
        }
        bookDao.update(bookFindById);

        bookFindById = bookDao.findById(2L).orElse(null);
        System.out.println(bookFindById);

        bookDao.deleteById(3L);
        bookList = bookDao.findAll();
        System.out.println(bookList);
    }
}
