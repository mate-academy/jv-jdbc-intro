package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        System.out.println(bookDao.findById(1L));
        System.out.println();
        Book programming = new Book();
        programming.setTitle("Java-8 Manual");
        programming.setPrice(new BigDecimal("245.3"));
        Book newBook = bookDao.create(programming);
        System.out.println(newBook);
        System.out.println();
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println();
        programming.setId(4L);
        programming.setTitle("Java-9 Manual");
        programming.setPrice(new BigDecimal("285.3"));
        bookDao.update(programming);
        System.out.println(bookDao.findById(2L));
        bookDao.deleteById(6L);
        books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println();

    }
}
