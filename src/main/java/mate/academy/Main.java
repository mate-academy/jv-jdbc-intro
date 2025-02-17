package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // I didn't use cascade type create/drop

        System.out.println("create() method ----------------------->");
        Book book = new Book();
        book.setTitle("Thinking in Java");
        book.setPrice(BigDecimal.valueOf(1999.90));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.create(book));

        System.out.println("findById() method ----------------------->");
        bookDao.findById(2L);

        System.out.println("findAll() method ----------------------->");
        List<Book> allBooks = bookDao.findAll();
        for (Book currentBook : allBooks) {
            System.out.println(currentBook);
        }

        System.out.println("deleteById() method ------------------------>");
        System.out.println("Is book deleted : " + bookDao.deleteById(2L));

        System.out.println("update() method ------------------------>");
        Book book1 = new Book();
        book1.setId(3L);
        book1.setTitle("Updated title");
        book1.setPrice(BigDecimal.valueOf(1999.90));
        System.out.println(bookDao.update(book1));
    }
}
