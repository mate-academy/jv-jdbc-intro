package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.dao.BookDaoImpl;
import mate.academy.lib.model.Book;

public class Main {
    public static void main(String[] args) {
        final var bookDao = new BookDaoImpl();
        final var book = new Book();
        book.setTitle("Book 4");
        book.setPrice(BigDecimal.valueOf(1000));
        book.setId(2L);

        System.out.println(bookDao.delete(2L));

    }
}
