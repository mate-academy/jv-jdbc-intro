package mate.academy;

import dao.BookDao;
import java.math.BigDecimal;
import mate.academy.lib.Injector;
import model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book myBook = new Book();
        myBook.setTitle("Wyprawa w Tatry");
        myBook.setPrice(BigDecimal.valueOf(49.99));

        Book savedBook = bookDao.create(myBook);
        System.out.println("Zapisano książkę z ID: " + savedBook.getId());

        System.out.println("Wszystkie książki: " + bookDao.findAll());
    }
}
