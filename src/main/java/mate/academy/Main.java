package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book myFirstBook = new Book();
        myFirstBook.setTitle("My first book");
        myFirstBook.setPrice(BigDecimal.valueOf(999.00));

        System.out.println("Create new book: " + bookDao.create(myFirstBook));
        System.out.println("Find all books: " + bookDao.findAll());
        Book bookById = bookDao.findById(1L).get();
        System.out.println("Find book by id: " + bookById);
        bookById.setTitle("Changed title");
        bookById.setPrice(BigDecimal.valueOf(888.00));
        System.out.println("Updated book: " + bookDao.update(bookById));
        System.out.println("Delete book with id: " + bookById.getId()
                + " result: " + bookDao.deleteById(bookById.getId()));
        System.out.println("Check DB after delete: " + bookDao.findAll());
    }
}
