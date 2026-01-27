package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Adventure");
        book.setPrice(BigDecimal.valueOf(12.5));
        Book bookUpdated = bookDao.create(book);
        System.out.println(bookUpdated);
        bookDao.deleteById(8L);
        bookDao.findById(15L)
                .ifPresent(System.out::println);
        bookUpdated.setPrice(BigDecimal.valueOf(220));
        bookDao.update(bookUpdated);
        bookDao.findAll().forEach(System.out::println);
    }
}
