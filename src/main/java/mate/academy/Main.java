package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final BookDao bookDao =
            (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Rich dad poor dad");
        book.setPrice(new BigDecimal(15));

        Book updatedBook = new Book(1L,
                "Harry Potter and the Philosopher's Stone", new BigDecimal(25));

        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.update(updatedBook));
        System.out.println(bookDao.deleteById(1L));
        bookDao.findAll().forEach(System.out::println);

    }
}
