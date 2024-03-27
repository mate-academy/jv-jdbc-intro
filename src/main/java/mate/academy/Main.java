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
        Book newBook = new Book();
        newBook.setTitle("Zakhar Berkut");
        newBook.setPrice(BigDecimal.valueOf(19.99));

        Book savedBook = bookDao.create(newBook);
        System.out.println("Saved book: " + savedBook);

        Optional<Book> optionalBook = bookDao.findById(savedBook.getId());
        System.out.println("Found by ID: " + optionalBook);

        optionalBook.ifPresent(b -> {
            b.setPrice(BigDecimal.valueOf(29.99));
            bookDao.update(b);
            System.out.println("Updated book: " + b);
        });

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books:");
        for (Book b : allBooks) {
            System.out.println(b);
        }

        boolean deletedBook = bookDao.deleteById(savedBook.getId());
        System.out.println("Book with id " + savedBook.getId() + " was deleted: " + deletedBook);
    }
}
