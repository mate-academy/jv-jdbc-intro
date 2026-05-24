package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Create a book
        Book book = new Book();
        book.setTitle("Thinking in Java");
        book.setPrice(BigDecimal.valueOf(450.50));
        bookDao.create(book);
        System.out.println("1. Book is created: " + book);

        //Find it
        System.out.println("2. Searching by Id: " + bookDao.findById(book.getId()));

        // Update the price
        book.setPrice(BigDecimal.valueOf(500.00));
        bookDao.update(book);
        System.out.println("3. After price updating: " + bookDao.findById(book.getId()));

        // Show out
        System.out.println("4. List of all books: " + bookDao.findAll());

        //Deleting
        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("5. Is deleting successfully?  " + deleted);
    }
}
