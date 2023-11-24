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
        firstBook.setTitle("The book");
        firstBook.setPrice(BigDecimal.valueOf(100));
        System.out.println("Created a new book : " + bookDao.create(firstBook));
        System.out.println("Find book by id: " + bookDao.findById(firstBook.getId()).get()
                + System.lineSeparator());

        Book secondBook = new Book();
        secondBook.setTitle("The second book");
        secondBook.setPrice(BigDecimal.valueOf(200));
        System.out.println("Created a new book : "
                + bookDao.create(secondBook) + System.lineSeparator());
        System.out.println("All books : "
                + bookDao.findAll() + System.lineSeparator());

        secondBook.setTitle("Changed title");
        System.out.println("Updated second book : "
                + bookDao.update(secondBook) + System.lineSeparator());

        System.out.println("Delete second book : "
                + bookDao.deleteById(secondBook.getId()) + System.lineSeparator());
        System.out.println("All books : "
                + bookDao.findAll() + System.lineSeparator());

    }
}
