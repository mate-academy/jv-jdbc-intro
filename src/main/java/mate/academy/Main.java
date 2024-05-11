package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        final BookDaoImpl bookDao = new BookDaoImpl();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Frodo and stuff");
        book.setPrice(new BigDecimal(100));
        bookDao.create(book);

        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresent(System.out::println);

        book.setTitle("Eldery stuff");
        bookDao.update(book);

        bookDao.deleteById(1L);

        bookDao.findAll().forEach(System.out::println);
    }
}
