package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookService = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Art Of War", BigDecimal.valueOf(330));
        Book book2 = new Book("Influence", BigDecimal.valueOf(440));
        Book book3 = new Book("Clean Code", BigDecimal.valueOf(550));

        bookService.create(book1);
        bookService.create(book2);
        bookService.create(book3);

        bookService.findById(book1.getId());

        bookService.findAll();

        Book updatedBook = new Book();
        updatedBook.setId(3L);
        updatedBook.setTitle("Not A Clean Code");
        updatedBook.setPrice(BigDecimal.valueOf(450));
        bookService.update(updatedBook);

        bookService.deleteById(updatedBook.getId());
    }
}
