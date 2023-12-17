package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book = new Book("Kill a mockingbird", BigDecimal.ONE);
        book = bookDao.create(book);
        System.out.println(book.getId());
        bookDao.findAll().forEach(System.out::println);
        book.setPrice(BigDecimal.ZERO);
        book = bookDao.update(book);
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(book.getId());
        bookDao.findAll().forEach(System.out::println);
    }
}
