package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("The Last of Us", new BigDecimal("149.99"));

        Book createdBook = bookDao.create(book);
        System.out.println("Created: " + createdBook);

        System.out.println("Find book by Id");
        bookDao.findById(createdBook.getId()).ifPresent(System.out::println);

        System.out.println("ALL BOOKS:");
        bookDao.findAll().forEach(System.out::println);
        System.out.println("ALL BOOKS WAS FOUND");

        createdBook.setPrice(new BigDecimal("199.99"));
        System.out.println("Update book: " + bookDao.update(createdBook));

        bookDao.deleteById(createdBook.getId());
    }
}
