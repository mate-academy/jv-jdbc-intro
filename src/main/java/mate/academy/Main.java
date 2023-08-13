package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book jjk = new Book();
        jjk.setTitle("jujutsu kaisen");
        jjk.setPrice(BigDecimal.valueOf(29.99));
        bookDao.create(jjk);

        Book onePunchMan = new Book();
        onePunchMan.setTitle("One punch man");
        onePunchMan.setPrice(BigDecimal.valueOf(25.99));
        bookDao.create(onePunchMan);

        Book basketball = new Book();
        basketball.setTitle("Kuroko no basket");
        basketball.setPrice(BigDecimal.valueOf(27.99));
        bookDao.create(basketball);

        System.out.println(bookDao.findAll());
        bookDao.deleteById(2L);
        Book updatedBook = new Book();
        updatedBook.setId(17L);
        updatedBook.setTitle("Attack on titan");
        updatedBook.setPrice(BigDecimal.valueOf(35.50));
        bookDao.update(updatedBook);
        System.out.println(bookDao.findAll());







    }
}
