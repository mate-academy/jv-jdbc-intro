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
        book.setTitle("The Little Prince");
        book.setPrice(BigDecimal.TEN);
        book = bookDao.create(book);

        Book bookFromDatabase = bookDao.findById(book.getId()).get();
        System.out.println(bookFromDatabase);

        Optional<Book> nonexistentBook = bookDao.findById(Long.MAX_VALUE);
        if (nonexistentBook.isEmpty()) {
            System.out.println("Nonexistent book!");
        }

        Book secondBook = new Book();
        secondBook.setTitle("The Second Book");
        secondBook.setPrice(BigDecimal.valueOf(5L));
        bookDao.create(secondBook);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        book.setPrice(BigDecimal.valueOf(8L));
        bookDao.update(book);

        bookDao.deleteById(secondBook.getId());
        System.out.println(bookDao.findAll());
    }
}
