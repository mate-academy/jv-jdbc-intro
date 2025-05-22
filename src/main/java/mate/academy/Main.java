package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        //testing
        BookDao bookDao = new BookDaoImpl();
        Book shake = new Book();
        shake.setTitle("Hello");
        shake.setPrice(BigDecimal.valueOf(22.10d));
        Book shakeUpdated = bookDao.create(shake);
        System.out.println(shakeUpdated);
    }
}
