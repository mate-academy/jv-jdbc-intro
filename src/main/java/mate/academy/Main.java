package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book b = new Book();
        b.setId(1L);
        b.setPrice(BigDecimal.valueOf(323));
        b.setTitle("Gandon");
        System.out.println(bookDao.update(b));
        System.out.println(bookDao.deleteById(1L));
    }
}
