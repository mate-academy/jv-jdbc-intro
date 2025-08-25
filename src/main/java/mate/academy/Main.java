package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Illiad", new BigDecimal(20));
        Book book2 = new Book("Eneida", new BigDecimal(32));

        Book dbBook1 = bookDao.create(book1);
        Book dbBook2 = bookDao.create(book2);

        System.out.println("Book with id " + dbBook1.getId()
                + " : " + bookDao.findById(dbBook1.getId()));
        System.out.println("Book with id " + dbBook2.getId()
                + " : " + bookDao.findById(dbBook2.getId()));

        dbBook1.setPrice(BigDecimal.valueOf(24));
        bookDao.update(dbBook1);

        System.out.println(System.lineSeparator() + "Updated list of books in db:");
        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(dbBook1.getId());
        bookDao.deleteById(dbBook2.getId());

        System.out.println(System.lineSeparator() + "Empty list of books in db:");
        bookDao.findAll().forEach(System.out::println);

    }
}
