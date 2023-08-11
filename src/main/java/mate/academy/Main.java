package mate.academy;

import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book = new Book(1L, "BooBook", BigDecimal.valueOf(500));
        Book createdBook = bookDao.create(book);
        Book updatedBook = bookDao.update(book);
        Optional<Book> foundBook = bookDao.findById(book.getId());
        List<Book> allBooks = bookDao.findAll();
        boolean isDeletedBook = bookDao.deleteById(book.getId());
    }
}
