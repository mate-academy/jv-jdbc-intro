package mate.academy;

import mate.academy.Dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.Dao");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Cat`s Cradle");
        book.setPrice(BigDecimal.valueOf(250));
        Book bookInDB = bookDao.create(book);
        System.out.println(bookInDB);
        bookInDB.setTitle("Slaughterhouse 5");
        bookInDB.setPrice(BigDecimal.valueOf(325));
        Book updatedBookInDB = bookDao.update(bookInDB);
        System.out.println(updatedBookInDB);
    }
}
