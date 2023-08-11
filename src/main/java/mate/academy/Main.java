package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Book");
        book.setPrice(BigDecimal.TEN);
        bookDao.create(book);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        book.setTitle("Book 1");
        bookDao.update(book);
        books = bookDao.findAll();
        System.out.println(books);
        bookDao.deleteById(book.getId());
        books = bookDao.findAll();
        System.out.println(books);
    }
}
