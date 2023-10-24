package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice(new BigDecimal("19.99"));
        book = bookDao.create(book);


        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresent(System.out::println);

        book.setTitle("Updated book Title");
        bookDao.update(book);

        boolean isDeleted = bookDao.delete(book);
        if (isDeleted) {
            System.out.println("Book deleted successfully");
        } else {
            System.out.println("Failed to delete a book");
        }
    }
}
