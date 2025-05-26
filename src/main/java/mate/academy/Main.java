package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Whispers of the Forgotten Garden");
        book1.setPrice(BigDecimal.valueOf(125));
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Echoes of the Silent Shore");
        book2.setPrice(BigDecimal.valueOf(220));
        bookDao.create(book2);

        Optional<Book> byId = bookDao.findById(2L);
        System.out.println(byId);

        book1.setPrice(BigDecimal.valueOf(345));
        bookDao.update(book1);

        List<Book> bookList = bookDao.findAll();
        System.out.println(bookList);

        Long id = book2.getId();
        System.out.println(bookDao.deleteById(id));

        List<Book> updatedBookList = bookDao.findAll();
        System.out.println(updatedBookList);

    }
}

