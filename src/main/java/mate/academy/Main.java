package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("Freat book", new BigDecimal(1499));
        Book book2 = new Book("Dark book", new BigDecimal(2000));

        bookDao.create(book1);
        bookDao.create(book2);

        System.out.println("Book found by id 1: " + bookDao.findById(1L));

        List<Book> bookList = bookDao.findAll();
        System.out.println("All books from DB: ");
        bookList.forEach(System.out::println);

        book1.setTitle("Great Book!");
        bookDao.update(book1);
        bookList = bookDao.findAll();
        System.out.println("After update: ");
        bookList.forEach(System.out::println);

        bookDao.deleteById(1L);
        bookList = bookDao.findAll();
        System.out.println("After delete by id 1: ");
        bookList.forEach(System.out::println);
    }
}
