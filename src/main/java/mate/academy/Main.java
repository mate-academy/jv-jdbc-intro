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
        Book book1 = new Book();
        book1.setTitle("Jane Eyre");
        book1.setPrice(BigDecimal.valueOf(250.6));
        Book bookUpd = bookDao.create(book1);
        System.out.println(bookUpd);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books:");
        for (Book book : allBooks) {
            System.out.println(book);
        }

        System.out.println(bookDao.findById(2L));

        System.out.println(bookDao.deleteById(1L));
    }
}
