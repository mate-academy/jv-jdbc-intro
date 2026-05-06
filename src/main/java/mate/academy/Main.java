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
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(399.99));

        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        System.out.println("Find by id: " + bookDao.findById(createdBook.getId()));

        System.out.println("All books: " + bookDao.findAll());

        createdBook.setTitle("Harry Potter Updated");
        createdBook.setPrice(BigDecimal.valueOf(499.99));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        System.out.println("Find updated book: " + bookDao.findById(updatedBook.getId()));

        boolean isDeleted = bookDao.deleteById(updatedBook.getId());
        System.out.println("Deleted: " + isDeleted);

        System.out.println("Find after delete: " + bookDao.findById(updatedBook.getId()));
    }
}
