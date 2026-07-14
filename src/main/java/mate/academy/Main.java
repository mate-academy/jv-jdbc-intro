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
        //CREATE
        Book firstBook = new Book("First Book", new BigDecimal(10));
        Book createdBook = bookDao.create(firstBook);
        System.out.println("Created book: " + createdBook);
        Book secondBook = new Book("First Book", new BigDecimal(10));
        bookDao.create(secondBook);
        //FIND BY ID
        Long bookId = createdBook.getId();
        Optional<Book> bookOptional = bookDao.findById(bookId);
        System.out.println("Found book: " + bookOptional.orElse(null));
        //UPDATE
        if (bookOptional.isPresent()) {
            Book bookToUpdate = bookOptional.get();
            bookToUpdate.setTitle("Updated Book");
            bookToUpdate.setPrice(new BigDecimal("15.00"));
            Book updatedBook = bookDao.update(bookToUpdate);
            System.out.println("Updated book: " + updatedBook);
        }
        //FIND ALL
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in DB:");
        allBooks.forEach(System.out::println);
        //DELETE
        boolean isDeleted = bookDao.deleteById(bookId);
        System.out.println("Is book deleted? " + isDeleted);
        System.out.println("Books after deletion:");
        bookDao.findAll().forEach(System.out::println);
    }
}
