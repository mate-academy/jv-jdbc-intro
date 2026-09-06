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
        book.setTitle("abc");
        book.setPrice(BigDecimal.valueOf(100));

        Book createdBook = bookDao.create(book);
        System.out.println("Created: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        System.out.println("Found by id: " + foundBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);

        createdBook.setTitle("updated title");
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Deleted: " + isDeleted);
    }
}
