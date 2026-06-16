package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(new BigDecimal("199.99"));

        Book createdBook = bookDao.create(book);
        System.out.println("Created: " + createdBook);

        System.out.println(
                "Found by id: "
                        + bookDao.findById(createdBook.getId())
        );

        System.out.println(
                "All books: "
                        + bookDao.findAll()
        );

        createdBook.setTitle("Harry Potter Updated");
        createdBook.setPrice(new BigDecimal("299.99"));

        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated: " + updatedBook);

        boolean deleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Deleted: " + deleted);
    }
}
