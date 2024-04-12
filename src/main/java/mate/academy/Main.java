package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        // initialize field values using setters or constructor
        book.setTitle("Tom and Jerry");
        book.setPrice(BigDecimal.valueOf(254));
        bookDao.create(book);

        Book book2 = new Book();
        book2.setTitle("Witcher 1");
        book2.setPrice(BigDecimal.valueOf(315));
        bookDao.create(book);

        Book book3 = new Book();
        book3.setTitle("Witcher 2");
        book3.setPrice(BigDecimal.valueOf(415));
        bookDao.create(book);

        // test other methods from BookDao
        //System.out.println(bookDao.findById(1L).get());
        List<Book> all = bookDao.findAll();
        printDB(all);

        Book updatedBook = all.get(2);
        updatedBook.setTitle("New World");
        updatedBook.setPrice(BigDecimal.valueOf(415.53));
        bookDao.update(updatedBook);
        printDB(all);

        bookDao.deleteById(updatedBook.getId());
        printDB(all);
    }

    static void printDB(List<Book> all) {
        System.out.println("------------------");
        for (var val: all) {
            System.out.println(val);
        }
    }
}
