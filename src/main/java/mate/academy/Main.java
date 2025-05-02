package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(123));
        book.setTitle("One history");
        Book oneHistory = bookDao.create(book);
        System.out.println(oneHistory);
        book.setPrice(BigDecimal.valueOf(12433));
        book.setTitle("Two history");
        Book twoHistory = bookDao.create(book);
        System.out.println(twoHistory);

        Book bookToUpdate = bookDao.findById(3L).orElseThrow(() ->
                new RuntimeException("Couldn't find the book")
        );
        System.out.println(bookToUpdate);
        bookToUpdate.setPrice(BigDecimal.valueOf(12421124));
        bookDao.update(bookToUpdate);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        System.out.println(bookDao.deleteById(3L));
    }
}
