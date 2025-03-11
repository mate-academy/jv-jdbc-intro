package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import util.DatabaseInitializer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book();
        firstBook.setTitle("Effective Java: Bloch, Joshua");
        firstBook.setPrice(new BigDecimal(15));
        bookDao.create(firstBook);

        Book secondBook = new Book();
        secondBook.setTitle("Clean code: Robert Martin");
        secondBook.setPrice(new BigDecimal(20));
        bookDao.create(secondBook);

        Book thirdBook = new Book();
        thirdBook.setTitle("The Pragmatic Programmer: David Thomas");
        thirdBook.setPrice(new BigDecimal(12));
        bookDao.create(thirdBook);

        thirdBook.setPrice(new BigDecimal(16));
        Book updatedBook = bookDao.update(thirdBook);
        System.out.printf("Price for book %s was updated to %s%n",
                thirdBook.getTitle(), updatedBook.getPrice());

        Optional<Book> thirdBookOptional = bookDao.findById(3L);
        thirdBookOptional.ifPresent(System.out::println);

        boolean isDeleted = bookDao.deleteById(3L);

        bookDao.findAll().forEach(System.out::println);

        if (isDeleted) {
            System.out.println((String.format(
                    "Book %s was deleted", thirdBook.getTitle())));
        }
    }
}
