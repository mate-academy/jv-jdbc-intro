package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        bookDao.findById(1L).ifPresentOrElse(book -> {
            System.out.println("Book found: " + book);

            book.setTitle("Alice in Wonderland");
            book.setPrice(new BigDecimal("200.00"));
            bookDao.update(book);

            System.out.println("Book updated: " + book);
        }, () -> {
            Book newBook = new Book();
            newBook.setPrice(new BigDecimal("100.00"));
            newBook.setTitle("Alice");
            bookDao.create(newBook);
            System.out.println("Book created: " + newBook);
        });

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in the database:");
        allBooks.forEach(System.out::println);

        boolean isDeleted = bookDao.deleteById(1L);
        if (isDeleted) {
            System.out.println("Book with ID 1 deleted.");
        } else {
            System.out.println("Book with ID 1 not found.");
        }
    }
}
