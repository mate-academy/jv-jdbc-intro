package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.Dao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao.impl");
    private static final String OPERATION_SEPARATOR = System.lineSeparator()
            + "------------------------------";

    public static void main(String[] args) {
        Dao<Book> bookDao = (BookDaoImpl) injector.getInstance(Dao.class);
        Book zeroBook = bookDao.create(new Book("Harry Potter 0", BigDecimal.valueOf(399.99)));
        System.out.println("First added book: " + zeroBook + OPERATION_SEPARATOR);

        Optional<Book> bookFromDbExists = bookDao.findById(zeroBook.getId());
        System.out.println("Retrieved book from DB: " + bookFromDbExists + OPERATION_SEPARATOR);

        bookDao.create(new Book("Harry Potter 1", BigDecimal.valueOf(499.99)));
        bookDao.create(new Book("Harry Potter 2", BigDecimal.valueOf(599.99)));
        bookDao.create(new Book("Harry Potter 3", BigDecimal.valueOf(699.99)));

        List<Book> bookList = bookDao.findAll();
        System.out.println("All books from DB: ");
        for (Book bookFromList : bookList) {
            System.out.println(bookFromList);
        }
        System.out.println(OPERATION_SEPARATOR);

        Book toBeUpdated = new Book(zeroBook.getId(),
                "Harry Potter UPDATED", BigDecimal.valueOf(799.99));
        Book updatedZeroBook = bookDao.update(toBeUpdated);
        System.out.println("Updated book " + updatedZeroBook + OPERATION_SEPARATOR);

        bookList = bookDao.findAll();
        System.out.println("All books from DB after UPDATE: ");
        for (Book bookFromList : bookList) {
            System.out.println(bookFromList);
        }
        System.out.println(OPERATION_SEPARATOR);
        bookDao.deleteById(updatedZeroBook.getId());

        bookList = bookDao.findAll();
        System.out.println("All books from DB after DELETE: ");
        for (Book bookFromList : bookList) {
            System.out.println(bookFromList);
        }
    }
}
