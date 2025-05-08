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
        book.setTitle("BookTitle");
        book.setPrice(new BigDecimal(152));

        bookDao.create(book);
        System.out.println(bookDao.findAll());

        Book newBook = new Book();
        newBook.setId(1L);
        newBook.setTitle("NewBookTitle");
        newBook.setPrice(new BigDecimal(999));

        bookDao.update(newBook);
        System.out.println(bookDao.findAll());

        bookDao.deleteById(1L);
        System.out.println(bookDao.findAll());
    }
}
