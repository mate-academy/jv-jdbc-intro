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
        System.out.println(bookDao.findById(1L));

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Siiuuuu");
        book.setPrice(BigDecimal.valueOf(200));
        Book updatedBook = bookDao.save(book);
        System.out.println(updatedBook);
        Optional<Book> foundBook = bookDao.findById(1L);
        System.out.println(foundBook);
        Book update = bookDao.update(book);
        System.out.println(update);
        boolean deleted = bookDao.deleteById(book);
        System.out.println(deleted);
    }
}
