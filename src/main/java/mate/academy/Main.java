package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.dao.Injector;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("PIPIXIA is a shrimp", BigDecimal.valueOf(111.00)),
                new Book("Dungeon master", BigDecimal.valueOf(300.00)),
                new Book("Average java enjoyer", BigDecimal.valueOf(150.50)),
                new Book("Lil JJ: Java Jebrony", BigDecimal.valueOf(9999.99))
        );

        List<Book> createdBooks = books.stream()
                .map(bookDao::create)
                .toList();

        Book book = bookDao.findById(createdBooks.get(1).getId())
                .orElseThrow(() -> new DataProcessingException("Book was not found"));
        System.out.println(" ");
        System.out.println(book);

        System.out.println(" ");
        bookDao.findAll().forEach(System.out::println);

        book.setTitle("Java Survival Evolved");
        Book updateBook = bookDao.update(book);
        System.out.println(" ");
        System.out.println(updateBook);

        bookDao.deleteById(createdBooks.get(0).getId());
        System.out.println(" ");
        bookDao.findAll().forEach(System.out::println);
    }
}
