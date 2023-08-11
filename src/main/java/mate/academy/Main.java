package mate.academy;


import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book blackSwan = new Book();
        blackSwan.setTitle("Black Swan");
        blackSwan.setPrice(BigDecimal.valueOf(700));
        Book lordOfTheRings = new Book();
        lordOfTheRings.setTitle("Lord of the Rings");
        lordOfTheRings.setPrice(BigDecimal.valueOf(1000));
        bookDao.create(blackSwan);
        bookDao.create(lordOfTheRings);
        List<Book> bookList = bookDao.findAll();
        bookDao.findById(1L);
        Book lordOfTheRingsTwoTowers = new Book();
        lordOfTheRingsTwoTowers.setId(2L);
        lordOfTheRingsTwoTowers.setPrice(BigDecimal.valueOf(1500));
        lordOfTheRingsTwoTowers.setTitle("Lord of The Rings: Two towers");
        bookDao.update(lordOfTheRingsTwoTowers);
        bookDao.deleteById(2L);
    }
}
