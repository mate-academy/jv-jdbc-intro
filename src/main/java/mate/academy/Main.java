package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final long LAST_BOOK = 3L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Thinking in Java");
        book.setPrice(BigDecimal.valueOf(19.99));
        bookDao.create(book);
        System.out.println(book);

        book = bookDao.findById(LAST_BOOK).orElse(null);
        System.out.println(book);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        book.setTitle("Thinking updated");
        bookDao.update(book);
        Book bookFromDB = bookDao.findById(LAST_BOOK).orElse(null);
        System.out.println(bookFromDB);

        boolean isDeleted = bookDao.deleteById(LAST_BOOK);
        System.out.println("Book is deleted " + isDeleted);

        books = bookDao.findAll();
        System.out.println("Remaining books after deletion: " + books);
    }
}
