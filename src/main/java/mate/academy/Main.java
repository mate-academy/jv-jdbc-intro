package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
        book1.setPrice(BigDecimal.valueOf(100));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.create(book1));
        Book book2 = new Book();
        book2.setTitle("Agile Software Development, Principles, Patterns, and Practices");
        book2.setPrice(BigDecimal.valueOf(101));
        System.out.println(bookDao.create(book2));
        Book book3 = new Book();
        book3.setTitle("The Clean Coder: A Code of Conduct for Professional Programmers");
        book3.setPrice(BigDecimal.valueOf(102));
        System.out.println(bookDao.create(book3));

        System.out.println("findById: ");
        for (int i = 1; i <= 3; i++) {
            Optional<Book> optionalBook = bookDao.findById((long) i);
            if (optionalBook.isPresent()) {
                System.out.println(optionalBook.get());
            } else {
                System.out.println("Book with id " + i + " not found");
            }
        }

        System.out.println("findAll: ");
        System.out.println(bookDao.findAll());

        System.out.println("deleteById: ");
        System.out.println("Return of method deleteById: " + bookDao.deleteById(3L));

        System.out.println("updateBook: ");
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Clean Architecture: "
                + "A Craftsman's Guide to Software Structure and Design");
        updatedBook.setPrice(BigDecimal.valueOf(50.00));
        Book updatedResult = bookDao.update(updatedBook);
        if (updatedResult != null) {
            System.out.println("The book was successfully updated: " + updatedResult);
        } else {
            System.out.println("Failed to update the book");
        }
    }
}
