package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(new BigDecimal("45.99"));
        book = bookDao.create(book);

        System.out.println("Created book: " + book);

        Optional<Book> fetchedBook = bookDao.findById(book.getId());
        fetchedBook.ifPresent(System.out::println);

        book.setPrice(new BigDecimal("49.99"));
        bookDao.update(book);
        System.out.println("Updated book: " + book);

        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Deleted: " + deleted);
    }
}
