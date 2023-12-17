package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book("Clean code", BigDecimal.valueOf(200.00));
        Book secondBook = new Book("Effective Java", BigDecimal.valueOf(365.47));
        Book thirdBook = new Book("Head First", BigDecimal.valueOf(124.24));

        // Insert books to DB
        Book firstBookDB = bookDao.create(firstBook);
        Book secondBookDB = bookDao.create(secondBook);
        Book thirdBookDB = bookDao.create(thirdBook);
        //find book by ID
        Book bookById = bookDao.findById(thirdBookDB.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Can't find book by id" + thirdBookDB.getId())
                );
        System.out.println(bookById);
        // Get all the books
        bookDao.findAll().forEach(System.out::println);
        //Update book
        Book updatedBook = bookDao.update(new Book(
                        firstBookDB.getId(),"Spring in action", BigDecimal.valueOf(93.85))
                );
        System.out.println(updatedBook);
        //Delete book by ID
        bookDao.deleteById(thirdBookDB.getId());
        //Get books after actions
        bookDao.findAll().forEach(System.out::println);
    }
}
