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

        System.out.println("--- 1. CREATE ---");
        Book novel = new Book();

        novel.setTitle("1984");
        novel.setPrice(new BigDecimal("250.50"));
        novel = bookDao.create(novel);
        System.out.println("Created book: " + novel);

        Book textbook = new Book();
        textbook.setTitle("Java Basics");
        textbook.setPrice(new BigDecimal("799.00"));
        bookDao.create(textbook);

        System.out.println("\n--- 2. FIND ALL ---");
        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);

        System.out.println("\n--- 3. UPDATE ---");
        Book bookToUpdate = allBooks.get(0);
        bookToUpdate.setTitle("1984 by George Orwell (Updated)");
        bookToUpdate.setPrice(new BigDecimal("300.00"));
        bookDao.update(bookToUpdate);

        System.out.println("Updated book: " + bookDao.findById(bookToUpdate.getId()).orElse(null));

        System.out.println("\n--- 4. FIND BY ID ---");
        Optional<Book> foundBook = bookDao.findById(bookToUpdate.getId());
        foundBook.ifPresent(b -> System.out.println("Found book: " + b));

        System.out.println("\n--- 5. DELETE ---");
        Long idToDelete = textbook.getId();
        boolean isDeleted = bookDao.deleteById(idToDelete);
        System.out.println("Book with ID " + idToDelete + " deleted: " + isDeleted);

        System.out.println("\n--- 6. FIND ALL (AFTER DELETE) ---");
        bookDao.findAll().forEach(System.out::println);
    }
}
