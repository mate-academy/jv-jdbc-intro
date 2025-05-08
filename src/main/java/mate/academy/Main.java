package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book(null, "Book1", new BigDecimal(100)),
                new Book(null, "Book2", new BigDecimal(150)),
                new Book(null, "Book3", new BigDecimal(50))
        );

        books.forEach(bookDao::create);
        System.out.println("Created books:");
        books.forEach(System.out::println);
        System.out.println();

        Long bookId = 2L;
        System.out.println("Book with id = [" + bookId + "]");
        bookDao.findById(bookId).ifPresent(System.out::println);
        System.out.println();

        System.out.println("All books in the table:");
        bookDao.findAll().forEach(System.out::println);
        System.out.println();

        Book anyBook = books.stream().findAny().orElseThrow();
        anyBook.setTitle("Updated title");
        Book anyUpdatedBook = bookDao.update(anyBook);
        System.out.println("Updated book:");
        System.out.println(anyUpdatedBook);
        System.out.println();

        boolean isDeleted = bookDao.deleteById(anyUpdatedBook.getId());
        System.out.println("Is book with id = ["
                + anyUpdatedBook.getId() + "] deleted = ["
                + isDeleted + "]");
    }
}
