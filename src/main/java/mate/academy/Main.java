package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book();
        book1.setTitle("Book1");
        book1.setPrice(BigDecimal.valueOf(200));

        Book book2 = new Book();
        book2.setTitle("Book2");
        book2.setPrice(BigDecimal.valueOf(300));

        //bookDao.create(book2);

        //bookDao.delete(2L);
        //bookDao.delete(3L);

        Book book3 = new Book(4L, "New Book", BigDecimal.valueOf(150));
        bookDao.update(book3);
        System.out.println(bookDao.findById(4L));

        List<Book> books = bookDao.findAll();
        System.out.println(books);
    }
}
