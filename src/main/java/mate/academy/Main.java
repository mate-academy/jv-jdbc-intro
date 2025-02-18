package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(null, "Java Programming", new BigDecimal("29.99"));
        book = bookDao.create(book);
        System.out.println("Created: " + book);

        bookDao.findAll().forEach(System.out::println);

        book.setTitle("Advanced Java");
        bookDao.update(book);
        System.out.println("Updated: " + bookDao.findById(book.getId()).get());

        bookDao.deleteById(book.getId());
        System.out.println("Deleted: " + bookDao.findById(book.getId()).orElse(null));
    }
}

