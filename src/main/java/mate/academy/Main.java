package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setPrice(BigDecimal.valueOf(500));
        firstBook.setTitle("The Book of books");
        System.out.println("Create: " + bookDao.create(firstBook));
        System.out.println("Find by id: " + bookDao.findById(firstBook.getId()).get()
                + System.lineSeparator());

        Book secondBook = new Book();
        secondBook.setPrice(BigDecimal.valueOf(230.99));
        secondBook.setTitle("A Simple Book");
        System.out.println("Create: " + bookDao.create(secondBook));
        System.out.println("Find all: " + bookDao.findAll()
                + System.lineSeparator());

        firstBook.setTitle("Changed book");
        firstBook.setPrice(BigDecimal.valueOf(600));
        System.out.println("First book updated: " + bookDao.update(firstBook)
                + System.lineSeparator());

        System.out.println("Is first book deleted: " + bookDao.deleteById(firstBook.getId()));
        System.out.println("Find all: " + bookDao.findAll());
    }
}
