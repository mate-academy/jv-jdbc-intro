package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book starWar = new Book();
        starWar.setTitle("Star War");
        starWar.setPrice(new BigDecimal("1950"));
        Book avatar = new Book();
        avatar.setTitle("Avatar");
        avatar.setPrice(new BigDecimal("5500"));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book starWarCreat = bookDao.create(starWar);
        Book avatarCreat = bookDao.create(avatar);

        Optional<Book> bookFindById = bookDao.findById(2L);
        System.out.println(bookFindById);

        List<Book> bookDaoAll = bookDao.findAll();
        System.out.println(bookDaoAll);

        boolean deleteById = bookDao.deleteById(2L);
    }
}
