package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("The Picture of Dorian Gray");
        book.setPrice(BigDecimal.valueOf(580));

        //Create operation
        System.out.println(bookDao.create(book));

        //Read operations
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(book.getId()));

        //Update operation
        book.setPrice(BigDecimal.valueOf(600));
        System.out.println(bookDao.update(book));

        //Delete operation
        System.out.println(bookDao.deleteById(book.getId()));
    }
}
