package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;


public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(110.5));

        Book createdBook = bookDao.create(book);

        Optional<Book> bookById = bookDao.findById(createdBook.getId());

        List<Book> books = bookDao.findAll();

        createdBook.setTitle("Harry Potter 2");
        Book updatedBook = bookDao.update(createdBook);

        boolean isDeleted = bookDao.deleteById(updatedBook.getId());
    }
}
