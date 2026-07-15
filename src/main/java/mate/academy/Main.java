package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        final Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Romeo and Juliet");
        book.setPrice(BigDecimal.valueOf(200));
        Book created = bookDao.create(book);
        System.out.println("Created book with id: " + created.getId());
        Optional<Book> found = bookDao.findById(created.getId());
        found.ifPresent(b -> System.out.println("Found book: " + b.getTitle()));
        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(b -> System.out.println(b.getId() + ": " + b.getTitle()));
        created.setPrice(BigDecimal.valueOf(250));
        Book updated = bookDao.update(created);
        System.out.println("Updated book price: " + updated.getPrice());
        boolean deleted = bookDao.deleteById(created.getId());
        System.out.println("Was book deleted? " + deleted);
    }
}
