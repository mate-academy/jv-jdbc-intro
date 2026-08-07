package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book cleanCode = new Book("Clean Code", BigDecimal.valueOf(41.50));
        Book createdCleanCode = bookDao.create(cleanCode);
        System.out.println("Created book: " + createdCleanCode);

        Book effectiveJava = new Book("Effective Java", BigDecimal.valueOf(45.99));
        Book createdEffectiveJava = bookDao.create(effectiveJava);
        System.out.println("Created book: " + createdEffectiveJava);

        bookDao.findById(createdCleanCode.getId())
                .ifPresent(book -> System.out.println("Found book: " + book));

        createdCleanCode.setPrice(BigDecimal.valueOf(39.99));
        Book updatedBook = bookDao.update(createdCleanCode);
        System.out.println("Updated book: " + updatedBook);

        System.out.println("All books: " + bookDao.findAll());

        boolean isDeleted = bookDao.deleteById(createdEffectiveJava.getId());
        System.out.println("Deleted book: " + isDeleted);
    }
}
