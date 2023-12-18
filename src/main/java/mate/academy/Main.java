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
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book vanessaYusMagicalTeaShopInParis = new Book();
        vanessaYusMagicalTeaShopInParis.setTitle("Vanessa Yu's Magical Tea Shop in Paris");
        vanessaYusMagicalTeaShopInParis.setPrice(BigDecimal.valueOf(700));

        Book redWhiteAndRoyalBlue = new Book();
        redWhiteAndRoyalBlue.setTitle("Red, White, and Royal Blue");
        redWhiteAndRoyalBlue.setPrice(BigDecimal.valueOf(1000));

        bookDao.create(vanessaYusMagicalTeaShopInParis);
        bookDao.create(redWhiteAndRoyalBlue);

        List<Book> bookList = bookDao.findAll();

        Optional<Book> bookById = bookDao.findById(1L);

        Book queensBladeDanceOfTheShadows = new Book();
        queensBladeDanceOfTheShadows.setId(2L);
        queensBladeDanceOfTheShadows.setPrice(BigDecimal.valueOf(1500));
        queensBladeDanceOfTheShadows.setTitle("Queen's Blade: Dance of the Shadows");

        Book updatedBook = bookDao.update(queensBladeDanceOfTheShadows);

        bookDao.deleteById(2L);
    }
}
