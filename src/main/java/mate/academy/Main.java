package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDaoImpl = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("OCP Oracle Certified Professional "
                + " Java SE 17 Developer Practice Tests: Exam 1Z0-829");
        book.setPrice(BigDecimal.valueOf(69.99));
        book = bookDaoImpl.create(book);

        System.out.println("Created book " + book);

        Optional<Book> desiredBook = bookDaoImpl.findById(book.getId());
        desiredBook.ifPresent(System.out::println);

        Book anotherBook = new Book();
        anotherBook.setTitle("UNIX");
        anotherBook.setPrice(BigDecimal.valueOf(39.90));
        anotherBook = bookDaoImpl.create(anotherBook);

        List<Book> books = bookDaoImpl.findAll();
        books.forEach(System.out::println);

        anotherBook.setPrice(BigDecimal.valueOf(35));
        bookDaoImpl.update(anotherBook);
        System.out.println("Update book price " + anotherBook);

        boolean deleteById = bookDaoImpl.deleteById(anotherBook.getId());
        System.out.printf("Deleted: " + deleteById);
    }
}
