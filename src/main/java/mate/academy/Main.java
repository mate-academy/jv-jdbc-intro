package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        Optional<Book> foundBook = bookDao.findById(1L);
        if (foundBook.isPresent()) {
            System.out.println("Book found: " + foundBook.get());
        } else {
            System.out.println("Book not found");
        }
        Book book = new Book();
        book.setId(1L);
        book.setPrice(new BigDecimal("100.00"));
        book.setTitle("Alice");
        bookDao.create(book);
        System.out.println("Book created: " + book);

        if (foundBook.isPresent()) {
            Book bookToUpdate = foundBook.get();
            bookToUpdate.setTitle("Alice in Wonderland");
            bookToUpdate.setPrice(new BigDecimal("120.00"));
            bookDao.update(bookToUpdate);
            System.out.println("Book updated: " + bookToUpdate);
        }

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in the database:");
        for (Book b : allBooks) {
            System.out.println(b);
        }

        boolean isDeleted = bookDao.deleteById(1L);
        if (isDeleted) {
            System.out.println("Book with ID 1 deleted.");
        } else {
            System.out.println("Book with ID 1 not found.");
        }
    }
}
