package mate.academy;

import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance(Main.class.getPackageName());
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book(null, "TBATE", BigDecimal.valueOf(250));
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created Book: " + createdBook);

        Optional<Book> bookById = bookDao.findById(createdBook.getId());
        System.out.println("Book found by ID: " + bookById);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);

        createdBook.setTitle("TBATE - Updated");
        createdBook.setPrice(BigDecimal.valueOf(300));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated Book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Book deleted: " + isDeleted);

        Optional<Book> deletedBookCheck = bookDao.findById(createdBook.getId());
        System.out.println("Deleted Book Check: " + deletedBookCheck);
    }
}
