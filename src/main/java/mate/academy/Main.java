package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("Some book 1", BigDecimal.valueOf(11.11));
        Book book2 = new Book("Some book 2", BigDecimal.valueOf(22.22));
        Book book3 = new Book("Some book 3", BigDecimal.valueOf(33.33));

        book1 = bookDao.create(book1);
        book2 = bookDao.create(book2);
        book3 = bookDao.create(book3);

        System.out.println("------------= CREATE =------------");
        System.out.println(book1 + " created!");
        System.out.println(book2 + " created!");
        System.out.println(book3 + " created!");
    }
}
