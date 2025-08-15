package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book();
        newBook.setTitle("Mate");
        newBook.setPrice(BigDecimal.valueOf(4.55));
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        System.out.println("Found book: " + foundBook.orElse(null));

        Book bookToUpdate = new Book();
        bookToUpdate.setId(createdBook.getId());
        bookToUpdate.setTitle("Mate 2nd Edition");
        bookToUpdate.setPrice(BigDecimal.valueOf(5.00));
        Book updatedBook = bookDao.update(bookToUpdate);
        System.out.println("Updated book: " + updatedBook);

        boolean deleted = bookDao.delete(updatedBook);
        System.out.println("Book deleted: " + deleted);
    }
}
