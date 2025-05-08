package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book cleanCode = new Book("Clean Code", BigDecimal.valueOf(200));
        bookDao.deleteById(1L);

        System.out.println(bookDao.findAll());
        bookDao.create(cleanCode);
        bookDao.create(cleanCode);
        System.out.println(bookDao.findAll());

        Book nonCleanCode = new Book("NonClean Code", BigDecimal.valueOf(200));
        bookDao.update(nonCleanCode);
    }
}
