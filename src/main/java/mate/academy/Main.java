package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.impl.BookServiceImpl;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);

        Book create = createWithRandomPrice("Test create method");
        bookService.create(create);
        System.out.println("Created method work as expected and return: " + create);

        Book readById = bookService.getById(create.getId());
        System.out.println("Read by id method work as expected and return: " + readById);

        List<Book> readAll = bookService.getAll();
        System.out.println("Read all method work as expected and return: " + readAll);

        create.setTitle("Updated");
        Book update = bookService.update(create);
        System.out.println("Updated method work as expected and return: " + update);

        boolean isDeleted = bookService.deleteById(update.getId());
        System.out.println("Deleted method work as expected and return: " + isDeleted);
    }

    private static Book createWithRandomPrice(String title) {
        Random random = new Random();
        BigDecimal price = new BigDecimal(random.nextInt(1000));

        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);

        return book;
    }
}
