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
        book.setPrice(BigDecimal.valueOf(250));

        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        System.out.println("Find by id: " + bookDao.findById(createdBook.getId()));
        System.out.println("Find all: " + bookDao.findAll());

        createdBook.setTitle("Harry Potter and the Philosopher's Stone");
        createdBook.setPrice(BigDecimal.valueOf(300));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(updatedBook.getId());
        System.out.println("Deleted: " + isDeleted);

        System.out.println("Find after delete: " + bookDao.findById(updatedBook.getId()));
    }
}
