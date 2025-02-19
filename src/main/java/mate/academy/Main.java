package mate.academy;

import java.math.BigDecimal;
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

        // FIND ALL
        List<Book> list = bookDao.findAll();
        System.out.println(list);

        // Create
        book.setTitle("Book Title");
        book.setPrice(BigDecimal.valueOf(241));
        bookDao.create(book);
        System.out.println(bookDao.findAll());

        // UPDATE
        book.setTitle("Book Title Udated");
        book.setPrice(BigDecimal.valueOf(103));
        book.setId(3L);
        bookDao.update(book);
        System.out.println(bookDao.findAll());

        // FIND BY ID
        Optional<Book> bookOptional = bookDao.findById(1L);
        System.out.println(bookOptional);

        // DELETE
        boolean deleteById = bookDao.deleteById(2L);
        System.out.println(deleteById);
        System.out.println(bookDao.findAll());
    }

}
