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

        Book book = new Book("Principles of Programming", new BigDecimal("100.00"));
        System.out.printf("New book created: %s",
                bookDao.create(book));
        System.out.println("\n\nLet's try to get our book by ID...");
        long id = book.getId();
        Optional<Book> actual = bookDao.findById(id);
        actual.ifPresentOrElse(
                value -> {
                    System.out.printf("Book found by id is: %s", value);
                },
                () -> {
                    System.out.println("Book not found");
                });

        System.out.println("\n\nLet's try to change our book...");
        book.setTitle("THE NEW Principles of Programming");
        bookDao.update(book);
        actual = bookDao.findById(id);
        actual.ifPresentOrElse(
                value -> {
                    System.out.printf("Updated book is: %s", value);
                },
                () -> {
                    System.out.println("Updated book not found");
                });

        System.out.println("\n\nLet's try to add some new books and select all of them.");
        Book book2 = new Book("The Clean Code", new BigDecimal("100.00"));
        Book book3 = new Book("Code Complete", new BigDecimal("200.00"));
        Book book4 = new Book("Philosophy of Java", new BigDecimal("100.00"));
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.create(book4);
        List<Book> books = bookDao.findAll();
        System.out.println("\nCurrently present books are: ");
        for (Book b : books) {
            System.out.println(b.toString());
        }

        System.out.println("\nLet's try to delete our book...");
        bookDao.deleteById(id);
        actual = bookDao.findById(id);
        actual.ifPresentOrElse(
                value -> {
                    System.out.printf("Book got by deleted id: %s", value);
                },
                () -> {
                    System.out.println("Deleted book not found.\n"
                            + "Deletion successful");
                });
    }
}
