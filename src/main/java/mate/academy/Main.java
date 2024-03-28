package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Dune");
        book.setPrice(BigDecimal.valueOf(19.99));
        bookDao.create(book);

        // Testing other methods
        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresent(System.out::println);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books: " + allBooks);

        book.setTitle("Lisan al gaib");
        bookDao.update(book);
        System.out.println("Updated Book: " + book);

        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Deleted: " + deleted);
    }
}
