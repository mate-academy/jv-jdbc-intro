package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.impl.BookServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);
        //creating books and adding to the table
        Book book1 = new Book();
        book1.setTitle("Java");
        book1.setPrice(BigDecimal.valueOf(435.44));
        bookService.create(book1);

        Book book2 = new Book();
        book2.setTitle("CleanCode");
        book2.setPrice(BigDecimal.valueOf(122.44));
        bookService.create(book2);

        // update book
        book1.setPrice(BigDecimal.valueOf(120));
        bookService.update(book1);

        // findById
        System.out.println(bookService.findById(16L));

        // findAll
        System.out.println(bookService.findAll());

        //delete by ID
        bookService.deleteById(21L);
        bookService.deleteById(22L);
    }
}
