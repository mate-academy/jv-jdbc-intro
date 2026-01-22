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
        book.setTitle("Java");
        book.setPrice(BigDecimal.valueOf(123));

        bookDao.create(book);
        System.out.println("Created a book with id: " + book.getId());

        System.out.println("FindById: " + bookDao.findById(book.getId()));

        book.setTitle("Java New Books");
        bookDao.update(book);

        System.out.println("FindAll: " + bookDao.findAll());

        System.out.println("Delete result: " + bookDao.deleteById(book.getId()));
    }
}
