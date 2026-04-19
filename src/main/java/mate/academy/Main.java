package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Book");
        book.setPrice(BigDecimal.valueOf(1200));
        Book book1 = bookDao.create(book);

        System.out.println(bookDao.findById(book1.getId()));

        bookDao.findAll().forEach(System.out::println);

        book1.setTitle("New book");
        bookDao.update(book1);

        bookDao.deleteById(book1.getId());
    }
}
