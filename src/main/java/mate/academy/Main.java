package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.dao.BookDao;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Harry Potter", BigDecimal.valueOf(200));
        Book book2 = new Book("book2", BigDecimal.valueOf(100));
        Book book3 = new Book("Book3", BigDecimal.valueOf(45));
        book1 = bookDao.create(book1);
        book2 = bookDao.create(book2);
        book3 = bookDao.create(book3);
        System.out.println(bookDao.findAll());
        bookDao.deleteById(book2.getId());
        System.out.println(bookDao.findAll());

        Book bookToUpdate = new Book(book2.getId(), "BookToUpdate", BigDecimal.valueOf(145));
        bookDao.update(bookToUpdate);
        System.out.println(bookDao.findAll());

    }
}
