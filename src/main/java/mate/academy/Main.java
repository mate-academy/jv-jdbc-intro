package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Java Programming", new BigDecimal("36.78"));
        Book updatedBook = bookDao.create(book);
        System.out.println("Created: " + updatedBook);

        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresent(b -> System.out.println("Found by ID " + b.getId() + ": " + b));

        book.setTitle("Advanced Java Programming");
        book.setPrice(new BigDecimal("43.99"));
        bookDao.update(book);
        System.out.println("Updated: " + book);

        System.out.println("All Books:");
        bookDao.findAll().forEach(System.out::println);

        boolean deleted = bookDao.delete(book.getId());
        System.out.println("Deleted: " + deleted);
    }
}
