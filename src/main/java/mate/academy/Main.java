package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Super title");
        book.setPrice(new BigDecimal("999.99"));

        book = bookDao.create(book);
        System.out.println("Create method: " + book);
        System.out.println("Find by id method: " + bookDao.findById(1L).get());
        System.out.println("Find all method: " + bookDao.findAll());

        book.setTitle("New Title!");
        book.setPrice(new BigDecimal("666.66"));

        System.out.println("Update method: " + bookDao.update(book));

        System.out.println("Delete by id method: " + bookDao.deleteById(1L));
    }
}
