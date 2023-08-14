package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);

        Book cleanCodeBook = new Book();
        cleanCodeBook.setTitle("Clean Code");
        cleanCodeBook.setPrice(BigDecimal.valueOf(29.99));
        bookDao.create(cleanCodeBook);

        Book colorOfMagicBook = new Book();
        colorOfMagicBook.setTitle("The color of magic");
        colorOfMagicBook.setPrice(BigDecimal.valueOf(69.99));
        bookDao.create(colorOfMagicBook);

        System.out.println(bookDao.findById(1L));

        colorOfMagicBook.setPrice(BigDecimal.valueOf(6.99));
        bookDao.update(colorOfMagicBook);

        System.out.println(bookDao.findAll());

        bookDao.deleteById(colorOfMagicBook.getId());

        bookDao.findAll().stream()
                .peek(b -> {
                    b.setPrice(b.getPrice().multiply(BigDecimal.valueOf(0.9)));
                    bookDao.update(b);
                }).forEach(System.out::println);
    }
}
