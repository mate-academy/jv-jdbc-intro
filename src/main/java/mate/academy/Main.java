package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(null, "Marry", 100);
        // initialize field values using setters or constructor
        bookDao.create(book);
        System.out.println(bookDao.deleteById(20L));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.update(new Book(1L, "Anton", 33)));
        System.out.println(bookDao.findById(1L));
    }
}
