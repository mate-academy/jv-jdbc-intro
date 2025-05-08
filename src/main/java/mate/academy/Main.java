package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("Effective Java");
        firstBook.setPrice(new BigDecimal("552.00"));

        Book secondBook = new Book();
        secondBook.setTitle("Clean Code");
        secondBook.setPrice(new BigDecimal("680.20"));

        Book thirdBook = new Book();
        thirdBook.setTitle("Head First");
        thirdBook.setPrice(new BigDecimal("890.50"));

        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        Optional<Book> bookById = bookDao.findById(1L);
        System.out.println(bookById);

        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        bookDao.deleteById(2L);
        books = bookDao.findAll();
        books.forEach(System.out::println);

        thirdBook.setTitle("Updated title");
        thirdBook.setPrice(new BigDecimal("1200.00"));
        Book updatedBook = bookDao.update(thirdBook);
        System.out.println(updatedBook);
    }
}
