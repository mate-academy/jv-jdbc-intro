package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book book = new Book();
        book.setTitle("Artem");
        book.setPrice(BigDecimal.valueOf(12313.321));
        System.out.println(bookDao.create(book));
    }
}
