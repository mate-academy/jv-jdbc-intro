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

        Book firstBook = new Book("First Book", BigDecimal.valueOf(120));
        Book secondBook = new Book("Second Book", BigDecimal.valueOf(220));
        Book thirdBook = new Book("Third Book", BigDecimal.valueOf(110));

        bookService.save(firstBook);
        bookService.save(secondBook);
        bookService.save(thirdBook);

        bookService.get(firstBook.getId());
        bookService.get(secondBook.getId());

        bookService.getAll();

        thirdBook.setPrice(BigDecimal.valueOf(420));
        bookService.update(thirdBook);
        bookService.delete(thirdBook);
    }
}
