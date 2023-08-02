package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book = new Book("book", BigDecimal.valueOf(999));
        Book createdBook = bookDao.create(book);
        System.out.println(createdBook);

        List<Book> getAllBook = bookDao.findAll();
        System.out.println("All Books : " + getAllBook);

        Optional<Book> getBookById = bookDao.findById(1L);
        System.out.println("Book by id " + getBookById);

        book.setTitle("Updated Book");
        book.setPrice(BigDecimal.valueOf(888));
        Book updatedBook = bookDao.update(book);
        System.out.println("Updated Book: " + updatedBook);

        boolean deleteBook = bookDao.deleteById(3L);
        System.out.println("Deleted book -> " + deleteBook);
    }
}
