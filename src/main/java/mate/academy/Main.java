package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // CREATE
        Book effectiveJava = new Book();
        effectiveJava.setId(1L);
        effectiveJava.setTitle("Effective Java");
        effectiveJava.setPrice(new BigDecimal("29.99"));
        bookDao.create(effectiveJava);

        Book coreJava = new Book();
        coreJava.setId(2L);
        coreJava.setTitle("Core Java");
        coreJava.setPrice(new BigDecimal("17.14"));
        bookDao.create(coreJava);

        // READ ALL
        List<Book> AllBooks = bookDao.findAll();
        System.out.println("All Books: " + AllBooks);

        // READ BY ID
        Optional<Book> foundBookById = bookDao.findById(2L);
        System.out.println("Found book: " + foundBookById);

        // UPDATE
        Book updatedBook = new Book();
        updatedBook.setId(1L); // ID of the existing book for updating
        updatedBook.setTitle("Java Concurrency in Practice");
        updatedBook.setPrice(new BigDecimal("19.90"));
        bookDao.update(updatedBook);

        System.out.println("After update: " + bookDao.findAll());

        // DELETE
        boolean isDeleted = bookDao.deleteById(2L);
        System.out.println("Book deleted: " + isDeleted);
        System.out.println("After deletion: " + bookDao.findAll());
    }
}
