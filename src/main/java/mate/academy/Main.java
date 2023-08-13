package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book = new Book(null, "Harry Potter", BigDecimal.valueOf(12.5));
        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);
        Book receivedBook = bookDao.findById(createdBook.getId()).orElseThrow(() ->
                new RuntimeException("Book with id" + createdBook.getId()
                        + "is not existed in DB"));
        System.out.println("Received book: " + receivedBook);
        System.out.println("All books from DB:");
        bookDao.findAll().forEach(System.out::println);
        createdBook.setTitle("Harry Potter 2");
        book = bookDao.update(createdBook);
        System.out.println("Updated book: " + book);
        System.out.println("Is deleted book with id " + book.getId()
                + ": " + bookDao.deleteById(book.getId()));
    }
}
