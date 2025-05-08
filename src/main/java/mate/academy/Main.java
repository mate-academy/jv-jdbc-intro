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
        book.setTitle("test");
        book.setPrice(BigDecimal.valueOf(59.99));

        Book savedBook = bookDao.create(book);
        System.out.println("Book saved: " + savedBook);

        Optional<Book> foundByIdBook = bookDao.findById(savedBook.getId());
        System.out.println("Book found: " + foundByIdBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks.size() + " books found");

        book.setPrice(BigDecimal.valueOf(99.99));
        Book updatedBook = bookDao.update(book);
        System.out.println("Book updated: " + updatedBook);

        boolean wasBookDeleted = bookDao.deleteById(book.getId());
        System.out.println("Attempt to delete book " + book + ". Result: " + wasBookDeleted);
    }
}
