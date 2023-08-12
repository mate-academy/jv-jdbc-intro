package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.sql.SQLOutput;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Inferno", 400);
        Book book2 = new Book((long)3, "Sherlock Holms", 230);

//        System.out.println(bookDao.findById((long) 1).get());
//        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findAll());
//        System.out.println(bookDao.deleteById((long) 2));
        System.out.println(bookDao.update(book2));
        System.out.println(bookDao.findAll());

    }
}
