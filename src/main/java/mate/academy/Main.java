package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy.dao.impl");

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(50));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        book = bookDao.create(book);
        System.out.println("Creating a book: ");
        System.out.println(bookDao.findById(book.getId()).toString());
        System.out.println();

        book.setPrice(BigDecimal.valueOf(100L));
        book.setTitle("1984");

        bookDao.update(book);
        System.out.println("All rows after updating the same book: ");
        bookDao.findAll().forEach(System.out::println);
        System.out.println();

        book.setPrice(BigDecimal.valueOf(95));
        book.setTitle("Lord of the Rings");

        bookDao.create(book);
        System.out.println("Creating another book: ");
        System.out.println(bookDao.findById(book.getId()).toString());
        System.out.println();

        System.out.println("All rows before deleting the book: ");
        bookDao.findAll().forEach(System.out::println);
        System.out.println();

        bookDao.deleteById(book.getId());

        System.out.println("All rows after deleting the book: ");
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
    }
}
