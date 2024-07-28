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

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        createBook(bookDao, "Test Book", BigDecimal.valueOf(230));
        createBook(bookDao, "Test Book2", BigDecimal.valueOf(530));

        List<Book> allBooks = bookDao.findAll();
        System.out.println("first list of books" + allBooks);

        Book bookToUpdate = new Book();
        bookToUpdate.setTitle("Test Book Update");
        bookToUpdate.setId(8L);

        Book updateOutcome = bookDao.update(bookToUpdate);
        System.out.println("update successful for book: " + updateOutcome);

        allBooks = bookDao.findAll();
        System.out.println("List of books after update" + allBooks);

        Optional<Book> bookById = bookDao.findById(8L);
        System.out.println("Book found by id: " + bookById);

        boolean deleteOutcome = bookDao.deleteById(8L);
        System.out.println("Was delete successful? " + deleteOutcome);

    }

    private static void createBook(BookDao bookDao, String title, BigDecimal price) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        bookDao.create(book);
    }
}
