package mate.academy;

import java.math.BigDecimal;
import mate.academy.daobook.DaoBook;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.daobook");

    public static void main(String[] args) {
        DaoBook daoBook = (DaoBook) injector.getInstance(DaoBook.class);
        Book book = new Book();
        book.setTitle("Mate");
        book.setPrice(BigDecimal.valueOf(500));
        daoBook.create(book);
        Book book2 = new Book();
        book2.setTitle("Academy");
        book2.setPrice(BigDecimal.valueOf(200));
        daoBook.create(book2);
        Book book3 = new Book();
        book3.setId(1L);
        book3.setTitle("Mate");
        book3.setPrice(BigDecimal.valueOf(400));
        daoBook.update(book);
        daoBook.findById(2L);
        daoBook.deleteById(1L);
    }
}
