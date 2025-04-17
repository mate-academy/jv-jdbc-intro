package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();

        Book book = new Book();
        book.setTitle("Effective Java");
        book.setPrice(new BigDecimal("59.90"));

        Book createdBook = bookDao.create(book);
        System.out.println("Created: " + createdBook);

        bookDao.findById(createdBook.getId()).ifPresent(System.out::println);

        createdBook.setPrice(new BigDecimal("49.90"));
        bookDao.update(createdBook);
        System.out.println("Updated: " + createdBook);

        System.out.println("All books: " + bookDao.findAll());

        bookDao.deleteById(createdBook.getId());
        System.out.println("Deleted book with ID: " + createdBook.getId());
    }
}
