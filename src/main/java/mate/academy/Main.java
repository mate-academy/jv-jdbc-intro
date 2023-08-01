package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookKobzar = createRecordManufacturer(
                bookDao, "Kobzar", BigDecimal.valueOf(200L));

        Book bookGaidamaki = createRecordManufacturer(
                bookDao, "Gaidamaki", BigDecimal.valueOf(150L));

        System.out.println(bookKobzar);

        bookKobzar.setPrice(BigDecimal.valueOf(400L));
        System.out.println(bookDao.update(bookKobzar));

        System.out.println(bookDao.findById(bookKobzar.getId()));

        System.out.println(bookDao.findAll());

        bookDao.deleteById(bookGaidamaki.getId());

        System.out.println(bookDao.findAll());
    }

    private static Book createRecordManufacturer(
            BookDao bookDao, String title, BigDecimal price) {
        Book manufacturer = new Book();
        manufacturer.setTitle(title);
        manufacturer.setPrice(price);
        return bookDao.create(manufacturer);
    }
}
