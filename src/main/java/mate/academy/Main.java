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
        Book book = new Book("Tom and Jerry", BigDecimal.valueOf(254));
        bookDao.create(book);

        Book book2 = new Book("Witcher 1", BigDecimal.valueOf(315));
        bookDao.create(book2);

        Book book3 = new Book("Witcher 2", BigDecimal.valueOf(415));
        bookDao.create(book3);

        System.out.println(bookDao.findById(1L).get());
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
