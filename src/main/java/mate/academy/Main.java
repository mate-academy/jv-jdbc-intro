package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final List<String> titles;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private static final int MIN_PRICE = 100;
    private static final int MAX_PRICE = 1000;

    static {
        titles = List.of(
                "Clean Code", "Effective Java", "Head First Java",
                "Thinking in Java", "Core Java", "Head First Design Patterns"
        );
    }

    public static void main(String[] args) {
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        List<Book> books = Stream.generate(() -> new Book(
                        titles.get(random.nextInt(titles.size())),
                        BigDecimal.valueOf(random.nextDouble(MIN_PRICE, MAX_PRICE))
                ))
                .limit(3)
                .toList();

        // insert books to DB
        List<Book> createdBooks = books.stream()
                .map(bookDao::create)
                .toList();

        // get book by id
        Book book = bookDao.findById(createdBooks.get(1).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);

        // get all books
        bookDao.findAll().forEach(System.out::println);

        // update book
        book.setTitle("Harry Potter");
        Book updateBook = bookDao.update(book);
        System.out.println(updateBook);

        // delete first book from list
        bookDao.deleteById(createdBooks.get(0).getId());
    }
}
