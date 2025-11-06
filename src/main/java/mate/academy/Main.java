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
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book();
        book1.setTitle("Warriors: Into the Wild");
        book1.setPrice(new BigDecimal("13.00"));
        Book createdBook1 = bookDao.create(book1);
        System.out.println("Created: " + createdBook1);

        Book book2 = new Book();
        book2.setTitle("Warriors: Fire and Ice");
        book2.setPrice(new BigDecimal("13.99"));
        Book createdBook2 = bookDao.create(book2);
        System.out.println("Created: " + createdBook2);

        Book book3 = new Book();
        book3.setTitle("Warriors: Forest of Secrets");
        book3.setPrice(new BigDecimal("7.91"));
        Book createdBook3 = bookDao.create(book3);
        System.out.println("Created: " + createdBook3);

        Optional<Book> foundBook = bookDao.findById(createdBook1.getId());
        foundBook.ifPresent(book -> System.out.println("Found book: " + book));

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books:");
        allBooks.forEach(System.out::println);

        createdBook1.setTitle("Warriors: Fire and Ice (with ARTBOOK)");
        createdBook1.setPrice(new BigDecimal("14.99"));
        Book updatedBook = bookDao.update(createdBook1);
        System.out.println("Updated: " + updatedBook);

        Optional<Book> verifyUpdate = bookDao.findById(createdBook1.getId());
        verifyUpdate.ifPresent(book -> System.out.println("Verified update: " + book));

        boolean isDeleted = bookDao.deleteById(createdBook2.getId());
        System.out.println("Book deleted: " + isDeleted);

        Optional<Book> deletedBook = bookDao.findById(createdBook2.getId());
        System.out.println("Book exists after deletion: " + deletedBook.isPresent());

        List<Book> remainingBooks = bookDao.findAll();
        remainingBooks.forEach(System.out::println);
    }
}
