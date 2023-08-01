package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book bookTJ = new Book();
        bookTJ.setTitle("Tom&Jerry");
        bookTJ.setPrice(BigDecimal.valueOf(200));
        Book bookCD = new Book();
        bookCD.setTitle("Chip&Dale");
        bookCD.setPrice(BigDecimal.valueOf(180));

        //CREATE
        bookDao.create(bookTJ);
        bookDao.create(bookCD);

        //READ
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(1L));

        //UPDATE
        bookCD = bookDao.findById(1L).orElse(new Book());
        bookCD.setTitle("Chip&Dale_new");
        bookCD.setPrice(BigDecimal.valueOf(530));
        bookDao.update(bookCD);

        //DELETE
        bookDao.deleteById(2L);
    }
}
