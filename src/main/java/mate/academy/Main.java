package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("On the Road");
        book.setPrice(new BigDecimal("19"));
        bookDao.create(book);
        Optional<Book> bookGet = bookDao.findById(2L);
        List<Book> books = bookDao.findAll();
        Book newBook = new Book();
        newBook.setTitle("The partner");
        newBook.setPrice(new BigDecimal("39"));
        bookDao.update(book);
        System.out.println(book);
        bookDao.deleteById(2L);
    }
}
