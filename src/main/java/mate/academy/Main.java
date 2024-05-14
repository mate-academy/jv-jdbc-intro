package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Heroes Land", new BigDecimal(15));
        Book savedBook = bookDao.create(book);
        System.out.println(savedBook);

        List<Book> bookList = bookDao.findAll();
        bookList.forEach(System.out::println);

        Book bookFromDB = bookDao.findById(1L).get();
        System.out.println(bookFromDB);

        bookFromDB.setPrice(new BigDecimal(25));
        Book updatedBook = bookDao.update(bookFromDB);
        System.out.println(updatedBook);

        boolean isDeleted = bookDao.deleteById(1L);
        System.out.println(isDeleted);
    }
}
