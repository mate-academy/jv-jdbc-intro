package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.DatabaseInitializer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        DatabaseInitializer.init();
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("--- 1. We will creating books ---");
        Book book1 = new Book("Head first Java", BigDecimal.valueOf(500));
        Book createdBook1 = bookDao.create(book1);
        System.out.println("Created book: " + createdBook1);

        Book book2 = new Book("Lord of Rings", BigDecimal.valueOf(450));
        Book createdBook2 = bookDao.create(book2);
        System.out.println("Created book: " + createdBook2);
        System.out.println("------------------------------------");

        System.out.println("--- 2. Looking for books ---");
        List<Book> allBooks = bookDao.findAll();
        System.out.println("Books in database: ");
        allBooks.forEach(System.out::println);
        System.out.println("------------------------------------");

        System.out.println("--- 3. Updating book data ---");
        createdBook1.setPrice(BigDecimal.valueOf(1500));
        Book updatedBook = bookDao.update(createdBook1);
        System.out.println("Updated book: " + updatedBook);
        System.out.println("------------------------------------");

        System.out.println("--- 4. Find book by ID ---");
        Optional<Book> foundBook = bookDao.findById(createdBook1.getId());
        foundBook.ifPresent(book -> System.out.println("Updated book was found " + book));
        System.out.println("------------------------------------");

        System.out.println("--- 5. Delete book by ID ---");
        boolean isDeleted = bookDao.deleteById(createdBook2.getId());
        System.out.println("Book with ID " + createdBook2.getId() + " was deleted: " + isDeleted);
        System.out.println("------------------------------------");

        System.out.println("--- 6. Checking remaining books ---");
        List<Book> remainingBooks = bookDao.findAll();
        System.out.println("Remaining books in database: ");
        remainingBooks.forEach(System.out::println);
        System.out.println("------------------------------------");
    }
}
