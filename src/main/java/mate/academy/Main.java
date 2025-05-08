package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;
import mate.academy.service.BookServiceImpl;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        BookServiceImpl bookService = new BookServiceImpl(bookDao);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(200));

        bookService.createBook(book);

        bookService.getBookById(1L);

        bookService.findAllBooks();

        book.setPrice(BigDecimal.valueOf(103.20));
        book.setTitle("JJJJJ");
        bookService.updateBook(1L, book);
    }
}
