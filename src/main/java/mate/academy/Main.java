package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = Book.of("Effective Java", new BigDecimal(700));
        Book secondBook = Book.of("Clean Code", new BigDecimal(900));
        Book thirdBook = Book.of("Head of Java", new BigDecimal(600));

        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        List<Book> booksFromDb = bookDao.findAll();
        System.out.println(booksFromDb);

        Optional<Book> bookById = bookDao.findById(3L);
        bookById.ifPresent(System.out::println);

        Book book = Book.of(3L, "Harry Potter", new BigDecimal(1000));
        Book updated = bookDao.update(book);
        System.out.println(updated);

        bookDao.findById(3L).ifPresent(System.out::println);

        boolean isBookDeleted = bookDao.deleteById(3L);
        System.out.println(isBookDeleted);
    }
}
