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

        System.out.println("Find by id 1 " + bookService.findById(1L));

        Book newBook = new Book("Цветы для Элджернона", BigDecimal.valueOf(350));
        Book createdBook = bookService.create(newBook);
        System.out.println("Created book " + createdBook);

        Book bookToUpdate = new Book(3L, "Тёмные начала", BigDecimal.valueOf(500));
        Book updated = bookService.update(bookToUpdate);
        System.out.println("Updated book " + updated);

        System.out.println("Is deleted book by id 2 " + bookService.deleteById(2L));

        for (Book book : bookService.findAll()) {
            System.out.println(book);
        }
    }
}
