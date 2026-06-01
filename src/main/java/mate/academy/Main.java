package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(500));

        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        System.out.println("Find by id: " + bookDao.findById(createdBook.getId()));

        System.out.println("All books: " + bookDao.findAll());

        createdBook.setTitle("Clean Code Updated");
        createdBook.setPrice(BigDecimal.valueOf(650));

        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Deleted: " + isDeleted);
    }
}
