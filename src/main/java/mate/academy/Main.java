package mate.academy;

import mate.academy.model.Book;
import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("Kobzar", BigDecimal.valueOf(100));
        Book book2 = new Book("Bukvar", BigDecimal.valueOf(80));
        Book book3 = new Book("Virshi", BigDecimal.valueOf(60));
        Book book4 = new Book("Eneida", BigDecimal.valueOf(100));

        System.out.println("Added - " + bookDao.create(book1));
        System.out.println("Added - " + bookDao.create(book2));
        System.out.println("Added - " + bookDao.create(book3));
        book4 = bookDao.create(book4);
        System.out.println("Added - " + book4);
        System.out.println("All books:");
        bookDao.findAll().forEach(System.out::println);
        Long id = book3.getId();
        System.out.println("Book with id = " + id + " is " + bookDao.findById(id).get());
        book4.setPrice(BigDecimal.valueOf(105));
        Book book4Updated = bookDao.update(book4);
        System.out.println("Book " + book4Updated + " is updated");
        System.out.println("Book with id = " + id + " is deleted: " + bookDao.delete(id));
        System.out.println("Book with id = " + id + " is deleted: " + bookDao.delete(id));
        System.out.println("All books after update and delete:");
        bookDao.findAll().forEach(System.out::println);
    }
}
