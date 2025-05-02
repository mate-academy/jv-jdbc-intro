package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.BookDao;
import model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book testBook = new Book();
        testBook.setTitle("firstBook");
        testBook.setPrice(BigDecimal.valueOf(100));

        Book createdBook = bookDao.create(testBook);
        System.out.println(createdBook);

        Book book1 = new Book();
        book1.setId(3L);
        book1.setTitle("update3");
        book1.setPrice(BigDecimal.valueOf(300));
        Book updatedBook = bookDao.update(book1);
        System.out.println(updatedBook);
        List<Book> books = bookDao.findAll();

        Optional<Book> bookOptional = bookDao.findById(1L);
        System.out.println(bookOptional);

        bookDao.deleteById(1L);
    }
}
