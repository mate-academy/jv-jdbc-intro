package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(29.99));

        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        System.out.println("Find by id:");
        System.out.println(bookDao.findById(createdBook.getId()).orElse(null));

        System.out.println("Find all:");
        System.out.println(bookDao.findAll());

        createdBook.setTitle("Clean Code Updated");
        createdBook.setPrice(BigDecimal.valueOf(35.50));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(updatedBook.getId());
        System.out.println("Deleted: " + isDeleted);

        System.out.println("Find after delete:");
        System.out.println(bookDao.findById(updatedBook.getId()).orElse(null));
    }
}
