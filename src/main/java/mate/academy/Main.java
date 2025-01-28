package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Catcher in the rye");
        book.setPrice(BigDecimal.valueOf(500));
        Book createdBook = bookDao.create(book);
        System.out.println("Book has been created: " + createdBook);
        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        System.out.println("Book has been found by the index "
                + createdBook.getId() + ": " + foundBook);
        book.setTitle("Crime and punishment");
        book.setPrice(BigDecimal.valueOf(750));
        Book updatedBook = bookDao.update(book);
        System.out.println("Updated book: " + updatedBook);
        List<Book> books = bookDao.findAll();
        System.out.println("Here is the list of all books: " + books);
        boolean isBookDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Is book with id " + createdBook.getId() + " deleted? " + isBookDeleted);
    }
}
