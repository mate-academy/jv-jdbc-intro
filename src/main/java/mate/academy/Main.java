package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("The Great Gatsby", BigDecimal.valueOf(300)),
                new Book("Animal Farm", BigDecimal.valueOf(150)),
                new Book("The Little Prince", BigDecimal.valueOf(200)),
                new Book("The Catcher in the Rye", BigDecimal.valueOf(400)),
                new Book("Lord of the Flies", BigDecimal.valueOf(120))
        );

        // insert new book to DB
        books.stream().map(bookDao::create).forEach(System.out::println);

        // get book from DB by id
        Book foundBook = bookDao.findById(3L).orElseThrow();
        System.out.println(foundBook);

        // find all books from DB
        List<Book> all = bookDao.findAll();
        all.stream().forEach(System.out::println);

        // update price on existing book
        Book theLittlePrince = bookDao.update(
                new Book(3L, "The Little Prince", BigDecimal.valueOf(100)));
        System.out.println(theLittlePrince);

        //delete book by existing id
        System.out.println(bookDao.deleteById(5L));

    }
}
