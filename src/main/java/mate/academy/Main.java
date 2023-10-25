package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book book1 = new Book("title", BigDecimal.valueOf(4));
        Book book2 = new Book("title2", BigDecimal.valueOf(4));
        Book newBook1 = bookDao.create(book1);
        System.out.println(newBook1);
        Book newBook2 = bookDao.create(book2);
        System.out.println(newBook2);
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);
        Optional<Book> book = bookDao.findById(1L);
        System.out.println(book);
        Book bookForUpdate = new Book("updateTitle", BigDecimal.valueOf(40));
        Book update = bookDao.update(bookForUpdate, 1L);
        System.out.println(update);
        boolean delete = bookDao.delete(book1);
        System.out.println(delete);
    }
}
