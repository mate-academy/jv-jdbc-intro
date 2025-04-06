package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book("How to program in Java", BigDecimal.valueOf(10));
        bookDao.create(newBook);

        Optional<Book> book = bookDao.findById(newBook.getId());
        book.ifPresent(newBook1 -> {
            newBook1.setPrice(BigDecimal.valueOf(12));
            bookDao.update(newBook1);
        });

        List<Book> books = bookDao.findAll();
        System.out.println("All books in the database:");
        for (Book allBook : books) {
            System.out.println(allBook.getId() + ": "
                    + allBook.getTitle() + " - "
                    + allBook.getPrice());
        }

        bookDao.deleteById(newBook.getId());
    }
}
