package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book kobzar = new Book("Kobzar", new BigDecimal("199.99"));
        Book theWitcher = new Book("The Witcher", new BigDecimal("259.99"));

        //add books to the database
        bookDao.create(kobzar);
        bookDao.create(theWitcher);

        //check whether the books have been added
        System.out.println(bookDao.findAll());

        Book fandorin = new Book(1L,"Fandorin", new BigDecimal("150.55"));

        //replace the Kobzar book with a new one
        bookDao.update(fandorin);

        //checked whether the book has been updated
        System.out.println(bookDao.findById(1L));

        //delete the book "The Witcher"
        bookDao.deleteById(2L);

        //check if all methods worked. Only the book "Fandorin" should remain
        System.out.println(bookDao.findAll());
    }
}
