package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.Injector;
import mate.academy.models.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Book book = createBook("Test Book", BigDecimal.valueOf(230));
        Book book2 = createBook("Test Book2", BigDecimal.valueOf(530));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.create(book2);
        List<Book> allBooks = bookDao.findAll();
        System.out.println("first list of books" + allBooks);

        book.setTitle("Test Book Update");
        book.setId(8L);

        Book updateOutcome = bookDao.update(book);
        System.out.println("update successful for book: " + updateOutcome);

        allBooks = bookDao.findAll();
        System.out.println("List of books after update" + allBooks);

        Optional<Book> bookById = bookDao.findById(8L);
        System.out.println("Book found by id: " + bookById);

        boolean deleteOutcome = bookDao.deleteById(8L);
        System.out.println("Was delete successful? " + deleteOutcome);

    }

    private static Book createBook(String title, BigDecimal price) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
