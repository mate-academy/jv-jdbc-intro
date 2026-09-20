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

        Book book = new Book()
                .setTitle("Harry Potter")
                .setPrice(new BigDecimal("25.50"));

        Book createdBook = bookDao.create(book);

        System.out.println("Created book:");
        System.out.println(createdBook);
        System.out.println("ID: " + createdBook.getId());
        System.out.println("\n========== FIND BY ID ==========");

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        System.out.println("Book with ID " + createdBook.getId() + ":");
        System.out.println(foundBook);

        System.out.println("\n========== FIND ALL ==========");
        List<Book> books = bookDao.findAll();
        System.out.println("All books:");
        books.forEach(System.out::println);

        System.out.println("\n========== UPDATE ==========");
        book.setTitle("Harry Potter - Updated");
        book.setPrice(new BigDecimal("39.99"));
        Book updatedBook = bookDao.update(book);
        System.out.println("Updated book:");
        System.out.println(updatedBook);

        System.out.println("\n========== CHECK UPDATE ==========");
        System.out.println("Book from database:");
        System.out.println(bookDao.findById(book.getId()));

        System.out.println("\n========== DELETE ==========");
        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Was book deleted? " + deleted);

        System.out.println("\n========== CHECK DELETE ==========");
        Optional<Book> deletedBook = bookDao.findById(book.getId());
        System.out.println("Book after deletion:");
        System.out.println(deletedBook);

        System.out.println("\n========== FINAL ==========");
        System.out.println("All books after deletion:");
        bookDao.findAll().forEach(System.out::println);
    }
}
