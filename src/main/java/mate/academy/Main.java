package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        // initialize field values using setters or constructor
        book.setTitle("Java");
        book.setPrice(BigDecimal.valueOf(100));
        Book createdBook = bookDao.create(book);
        System.out.println("Book created: " + createdBook);
        System.out.println("-----------------------------------------------------");
        // test other methods from BookDao
        Optional<Book> optionalBook1 = bookDao.findById(5L);
        System.out.println("book id = 5: " + optionalBook1);
        Optional<Book> optionalBook = bookDao.findById(100L);
        System.out.println("book id = 100: " + optionalBook);
        System.out.println("-----------------------------------------------------");
        List<Book> allBooks = new ArrayList<>();
        allBooks = bookDao.findAll();
        System.out.println("All books found: ");
        allBooks.forEach(System.out::println);
        System.out.println("------------------------------------------------------");

        Book bookToUpdate = new Book();
        bookToUpdate.setId(25L);
        bookToUpdate.setTitle("Updated Book");
        bookToUpdate.setPrice(BigDecimal.valueOf(5000));
        System.out.println(bookDao.update(bookToUpdate));
        System.out.println("-------------------------------------------------------");

        bookDao.deleteById(25L);
        System.out.println(bookDao.findById(25L));
    }
}
