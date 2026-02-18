package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("111 Facts", BigDecimal.valueOf(15.40));

        bookDao.create(book);

        book.setTitle("101 Facts about Life");

        bookDao.update(book);

        System.out.println(bookDao.findById(2L));

        bookDao.deleteById(13L);

        bookDao.findAll().forEach(System.out::println);
    }
}
