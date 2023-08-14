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
        book.setTitle("Java to Wood People");
        book.setPrice(BigDecimal.valueOf(350));

        //Create
        Book createdBook = bookDao.create(book);

        //findById
        Optional<Book> bookById = bookDao.findById(3L);
        System.out.println(bookById);

        //findAll
        List<Book> allBooksFromDb = bookDao.findAll();

        //update
        book.setTitle("JDBC for wood people, Part2");
        book.setPrice(BigDecimal.valueOf(500));
        Book updatedBook = bookDao.update(book);

        //delete
        bookDao.deleteById(4L);
    }
}
