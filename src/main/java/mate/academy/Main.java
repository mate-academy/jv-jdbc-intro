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
        //Add first book to the DB;
        Book firstBook = bookDao.create(new Book("Harry Potter 1", BigDecimal.valueOf(399.99)));
        System.out.println("First added book: " + firstBook + OPERATION_SEPARATOR);
        //Retrieve it from DB;
        Optional<Book> bookFromDbExists = bookDao.findById(firstBook.getId());
        System.out.println("Retrieved book from DB: " + bookFromDbExists + OPERATION_SEPARATOR);
        //Add three more books;
        bookDao.create(new Book("Harry Potter 2", BigDecimal.valueOf(499.99)));
        bookDao.create(new Book("Harry Potter 3", BigDecimal.valueOf(599.99)));
        bookDao.create(new Book("Harry Potter 4", BigDecimal.valueOf(699.99)));
        //Have a list of all books, if code run for the time, 4 books expected;
        List<Book> bookList = bookDao.findAll();
        System.out.println("All books from DB: ");
        for (Book bookFromList : bookList) {
            System.out.println(bookFromList);
        }
        System.out.println(OPERATION_SEPARATOR);
        //Update first book;
        Book toBeUpdated = new Book(firstBook.getId(),
                "Harry Potter UPDATED", BigDecimal.valueOf(799.99));
        Book updatedZeroBook = bookDao.update(toBeUpdated);
        System.out.println("Updated book " + updatedZeroBook + OPERATION_SEPARATOR);
        //And make sure it is updated in the DB by listing all books;
        bookList = bookDao.findAll();
        System.out.println("All books from DB after UPDATE: ");
        for (Book bookFromList : bookList) {
            System.out.println(bookFromList);
        }
        System.out.println(OPERATION_SEPARATOR);
        //Delete updated book;
        bookDao.deleteById(updatedZeroBook.getId());
        //And make sure it is deleted from the DB by listing all books;
        bookList = bookDao.findAll();
        System.out.println("All books from DB after DELETE: ");
        for (Book bookFromList : bookList) {
            System.out.println(bookFromList);
        }
    }
}
