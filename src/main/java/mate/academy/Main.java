package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        System.out.println("create method test");
        bookDao.create(new Book("1984 ", new BigDecimal("205.99")));
        bookDao.create(new Book("The Lord of the Rings ", new BigDecimal("250.99")));
        bookDao.create(new Book("Animal Farm ", new BigDecimal("300.99")));
        bookDao.create(new Book("The Catcher in the Rye ", new BigDecimal("150.99")));
        System.out.println("findById method test");
        bookDao.findById(3L).ifPresent(System.out::println);
        System.out.println("delete method test:");
        bookDao.deleteById(4L);
        System.out.println("update method test:");
        Book book = new Book(1L,"1984", new BigDecimal("555.99"));
        bookDao.update(book);
    }
}
