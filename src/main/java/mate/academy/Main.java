package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        System.out.println("Creating a new book:");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Treasure Island");
        book.setPrice(BigDecimal.valueOf(1500));
        System.out.println(bookDao.create(book));
        System.out.println("Getting book by id:");
        Optional<Book> optionalBook = bookDao.findById(3L);
        System.out.println(optionalBook.orElseThrow());
        System.out.println("Getting all books:");
        List<Book> books = bookDao.findAll();
        for (Book row : books) {
            System.out.println(row);
        }
        System.out.println("Book with id = 1 before updating:");
        System.out.println(bookDao.findById(4L).orElseThrow());
        System.out.println("Book with id = 1 after updating:");
        Book updatedBook = new Book();
        updatedBook.setId(4L);
        updatedBook.setTitle("New title");
        updatedBook.setPrice(BigDecimal.valueOf(2000));
        System.out.println(bookDao.update(updatedBook));
        System.out.println("Deleting book with id = 1:");
        System.out.println(bookDao.deleteById(4L));
    }
}
