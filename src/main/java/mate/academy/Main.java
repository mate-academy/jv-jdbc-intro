package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

/**
 * У main отримай BookDao через injector і протестуй всі CRUD-методи:
 * create → findById → findAll → update → deleteById.
 */
public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao dao = new BookDaoImpl();
        Optional<Book> byId = dao.findById(2L);
        byId.ifPresent(System.out::println);

        Book book = new Book();
        book.setTitle("J.London, White fang");
        book.setPrice(BigDecimal.valueOf(17.74));
        dao.create(book);
        System.out.println(book);
    }
}
