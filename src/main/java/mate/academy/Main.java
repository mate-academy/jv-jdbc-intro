package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        Book book = new Book();
        book.setId(1);
        book.setTitle("Триумфальна акрка");
        book.setPrice(BigDecimal.valueOf(235.00));
        Book book2 = new Book();
        book2.setId(2);
        book2.setTitle("Зелена миля");
        book2.setPrice(BigDecimal.valueOf(350.00));

        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);

        System.out.println(bookDao.create(book) + System.lineSeparator());
        System.out.println(bookDao.create(book2) + System.lineSeparator());
        System.out.println(bookDao.findById(2L) + System.lineSeparator());
        System.out.println(bookDao.findAll() + System.lineSeparator());
        System.out.println(bookDao.update(new Book(1L, "Три товариші", BigDecimal.valueOf(310)))
                + System.lineSeparator());
        System.out.println(bookDao.deleteById(2L));
    }
}
