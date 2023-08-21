package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book addedBook = new Book("Bible", BigDecimal.valueOf(49.99));
        Book updBook = new Book(7L,"It's a real Java", BigDecimal.valueOf(44.99));

        System.out.println(bookDao.create(addedBook));
        System.out.println(bookDao.findById(3L));
        System.out.println(bookDao.findAll());
        System.out.println("The book is updated: " + bookDao.update(updBook));
        System.out.println(bookDao.deleteById(10L));
    }

}
