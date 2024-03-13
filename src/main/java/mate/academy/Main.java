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
        for (int i = 0; i < 10; i++) {
            book.setTitle("Cat`s Cradle. Part " + (i + 1));
            book.setPrice(BigDecimal.valueOf(250));
            bookDao.create(book);
        }
        Book bookInDB = bookDao.findById(4L).get();
        System.out.println(bookInDB);
        bookInDB.setTitle("Slaughterhouse 5");
        bookInDB.setPrice(BigDecimal.valueOf(325));
        Book updatedBookInDB = bookDao.update(bookInDB);
        System.out.println(updatedBookInDB);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        bookDao.deleteById(2L);
    }
}
