package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book("First Book", BigDecimal.valueOf(30));
        newBook = bookDao.create(newBook);
        System.out.println("Created new book: " + newBook);

        Long id = newBook.getId();

        Book bookForUpdate = new Book(id, "Updated book", BigDecimal.valueOf(100));
        System.out.println("Updated 'First Book': " + bookDao.update(bookForUpdate));

        System.out.println("All book from DB: " + bookDao.findAll());

        System.out.println("Book by id " + id + ": " + bookDao.findById(id));

        System.out.println("Is book deleted by id " + id + ": " + bookDao.deleteById(id));

        System.out.println("Try to find book by id " + id + ": " + bookDao.findById(id));
    }
}
