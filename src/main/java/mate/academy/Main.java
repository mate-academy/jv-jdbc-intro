package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = bookDao.create(new Book("White Fang", new BigDecimal(500)));
        bookDao.update(book1);
        Book book2 = bookDao.create(new Book("The Picture of Dorian Gray", new BigDecimal(600)));
        Book book3 = bookDao.create(new Book("Shantaram", new BigDecimal(400)));
        bookDao.deleteById(book3.getId());
        bookDao.findAll().forEach(System.out::println);
        book2.setPrice(new BigDecimal(1000));
        System.out.println(bookDao.findById(book2.getId()));
        bookDao.findAll().forEach(System.out::println);
    }
}

