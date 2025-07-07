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

        Book firstBook = new Book();
        firstBook.setTitle("The Little Prince");
        firstBook.setPrice(new BigDecimal("15.99"));
        Book createdBook = bookDao.create(firstBook);
        System.out.println("Created book: " + createdBook);

        Long bookId = createdBook.getId();
        Optional<Book> foundBookOptional = bookDao.findById(bookId);

        if (foundBookOptional.isPresent()) {
            System.out.println("Find book by id: " + foundBookOptional.get());
        } else {
            System.out.println("Book with id" + bookId + " not found.");
        }

        Book bookToUpdate = foundBookOptional.get();
        bookToUpdate.setTitle("The Little Prince (Updated Edition)");
        bookToUpdate.setPrice(new BigDecimal("19.99"));
        Book updatedBook = bookDao.update(bookToUpdate);

        System.out.println("Updated book: " + updatedBook);

        Book secondBook = new Book();
        secondBook.setTitle("Tom Sawyer");
        secondBook.setPrice(new BigDecimal("15.99"));
        bookDao.create(secondBook);

        List<Book> allBooks = bookDao.findAll();

        System.out.println("All books:");
        for (Book book : allBooks) {
            System.out.println(book);
        }

        boolean isDeleted = bookDao.deleteById(bookId);
        System.out.println("Book with ID " + bookId + " is deleted: " + isDeleted);

        Optional<Book> deletedBookOptional = bookDao.findById(bookId);

        if (deletedBookOptional.isPresent()) {
            System.out.println("Error: Book with ID " + bookId + " still exists.");
        } else {
            System.out.println("Success: Book with ID " + bookId + " not found.");
        }

        List<Book> updatedBooksList = bookDao.findAll();

        System.out.println("All books after update:");
        for (Book book : updatedBooksList) {
            System.out.println(book);
        }
    }
}
