package mate.academy;

import java.math.BigDecimal;
import java.math.BigInteger;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(new BigDecimal("50.45"));
        bookDao.create(book);

        System.out.println(bookDao.findById(BigInteger.valueOf(1)));

        Book updatedBook = new Book();
        updatedBook.setId(BigInteger.valueOf(1));
        updatedBook.setTitle("Design patterns");
        updatedBook.setPrice(BigDecimal.valueOf(30.50));
        bookDao.update(updatedBook);

        bookDao.deleteById(BigInteger.valueOf(2));
        System.out.println(bookDao.findAll());
    }
}
