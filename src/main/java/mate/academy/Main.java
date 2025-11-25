package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Java Basics", new BigDecimal("10.99"));
        bookDao.create(book);
        System.out.println("Created: " + book);

        System.out.println("Find by id: " + bookDao.findById(book.getId()));

        book.setTitle("Advanced Java");
        bookDao.update(book);
        System.out.println("Updated: " + book);

        System.out.println("All books: " + bookDao.findAll());

        bookDao.deleteById(book.getId());
        System.out.println("Deleted book " + book.getId());
    }
}
