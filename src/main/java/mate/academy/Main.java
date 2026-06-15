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
        book.setTitle("To Kill a Mockingbird");
        book.setPrice(new BigDecimal(500));

        // CREATE
        Book createdBook = bookDao.create(book);
        System.out.println("CREATED: " + createdBook);

        // FIND ALL
        bookDao.findAll().forEach(el -> System.out.println("FOUND ALL: " + el));

        // UPDATE
        createdBook.setPrice(new BigDecimal(900));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("UPDATED: " + updatedBook);

        // FIND BY ID
        bookDao.findById(book.getId())
                .ifPresent(el -> System.out.println("FOUND BY ID: " + el));

        // DELETE
        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("DELETED: " + (deleted));
    }
}
