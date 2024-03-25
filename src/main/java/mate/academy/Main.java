package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao dao = (BookDao) injector.getInstance(BookDao.class);

        for (int i = 0; i < 100; i++) {
            Book book = new Book();
            book.setTitle("Title" + i);
            book.setPrice(BigDecimal.valueOf(i * 100));
            dao.create(book);
        }

        for (int i = 30; i < 50; i++) {
            System.out.println(dao.findById((long)i));
        }

        System.out.println(dao.findAll());

        for (int i = 50; i < 70; i++) {
            dao.update(new Book((long)i, "NEWBOOK", BigDecimal.valueOf(77777777)));
        }

        for (int i = 0; i < 10000; i++) {
            dao.deleteById((long)i);
        }
    }
}
