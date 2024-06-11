package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);
        Book book = new Book("Test book", BigDecimal.valueOf(10));
        System.out.println(bookService.create(book));
        System.out.println(bookService.findById(1L));
        System.out.println(bookService.findAll());
        Book book2 = new Book("Test book2", BigDecimal.valueOf(20));
        book2.setId(1L);
        System.out.println(bookService.update(book2));
        System.out.println(bookService.deleteById(1L));
    }
}
