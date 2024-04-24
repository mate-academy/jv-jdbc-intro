package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book("Harry Potter", BigDecimal.valueOf(200));
        Book secondBook = new Book("book2", BigDecimal.valueOf(100));
        Book thirdBook = new Book("Book3", BigDecimal.valueOf(45));
        firstBook = bookDao.create(firstBook);
        secondBook = bookDao.create(secondBook);
        thirdBook = bookDao.create(thirdBook);
        System.out.println(bookDao.findAll());
        bookDao.deleteById(secondBook.getId());
        System.out.println(bookDao.findAll());

        Book bookToUpdate = new Book(secondBook.getId(), "BookToUpdate", BigDecimal.valueOf(145));
        bookDao.update(bookToUpdate);
        System.out.println(bookDao.findAll());

    }
}
