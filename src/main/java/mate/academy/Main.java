package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book bookTomSoyer = new Book();
        bookTomSoyer.setTitle("Tom Soyer");
        bookTomSoyer.setPrice(BigDecimal.valueOf(150.0));

        Book bookHarryPotter = new Book();
        bookHarryPotter.setTitle("Harry Potter");
        bookHarryPotter.setPrice(BigDecimal.valueOf(180.0));

        Book bookHobbit = new Book();
        bookHobbit.setTitle("Hobbit");
        bookHobbit.setPrice(BigDecimal.valueOf(120.0));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // Create
        //bookDao.create(bookTomSoyer);
        //bookDao.create(bookHarryPotter);

        // Show all
        System.out.println("\nShow all");
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }

        // Show by id
        System.out.println("\nShow by id");
        System.out.println("Book1: " + bookDao.findById(17L).get());
        System.out.println("Book2: " + bookDao.findById(18L).get());

        // Update the price
        Book updatedBook = bookDao.findById(17L)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        updatedBook.setPrice(BigDecimal.valueOf(200.0));
        bookDao.update(updatedBook);

        // Show by id
        System.out.println("\nShow by id:");
        System.out.println("Book1: " + bookDao.findById(1L));
        System.out.println("Book2: " + bookDao.findById(17L));

        // DeleteById
        System.out.println("\nDelete by id:");
        bookDao.deleteById(18L);
        System.out.println("\nShow all");
        List<Book> books2 = bookDao.findAll();
        for (Book book : books2) {
            System.out.println(book);
        }

    }
}
