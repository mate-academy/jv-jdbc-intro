package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Head First Java");
        book.setPrice(BigDecimal.valueOf(14.50));

        Book createdBook = bookDao.create(book);
        System.out.println("Created a book: " + createdBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("Books list: " + allBooks);

        Optional<Book> bookById = bookDao.findById(book.getId());
        System.out.println("Found book by id: " + book.getId() + ". The book: " + bookById);

        createdBook.setTitle("Java 8 in action");
        createdBook.setPrice(BigDecimal.valueOf(12.99));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("The book by id - " + book.getId() + " is deleted: " + isDeleted);
    }
}
