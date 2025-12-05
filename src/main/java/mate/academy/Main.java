package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao dao = (BookDao) INJECTOR.getInstance(BookDao.class);

        Book book1 = new Book("Pride and Prejudice", new BigDecimal("17.99"));
        Book book2 = new Book("1984", new BigDecimal("12.49"));
        Book book3 = new Book("The Lord of the Rings", new BigDecimal("24.19"));
        dao.create(book1);
        dao.create(book2);
        dao.create(book3);

        dao.update(new Book(5L, "The Silent Patient", new BigDecimal("32.01")));

        dao.deleteById(2L);

        System.out.println(dao.findById(2L));
        System.out.println(dao.findAll());
    }
}
