package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(123.10));
        bookDao.create(book);

        Book book1 = new Book();
        book1.setTitle("Пригоди Шерлока Холмса");
        book1.setPrice(BigDecimal.valueOf(250.50));
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Хобіт");
        book2.setPrice(BigDecimal.valueOf(350.99));
        bookDao.create(book2);

        List<Book> listAllBooks = bookDao.findAll();
        System.out.println("=== List of all books ===");
        for (Book elem : listAllBooks) {
            System.out.println(elem.toString());
        }

        Long id = 1L;
        Optional<Book> bookOnId = bookDao.findById(id);
        if (bookOnId.isEmpty()) {
            System.out.println("Book with id = " + id + " is absent in DB");
        } else {
            System.out.println("Book found with id = " + id + " is " + bookOnId.toString());
        }

        id = 100L;
        bookOnId = bookDao.findById(id);
        if (bookOnId.isEmpty()) {
            System.out.println("Book with id = " + id + " is absent in DB");
        } else {
            System.out.println("Book found with id = " + id + " is " + bookOnId.toString());
        }

        Book bookForUpdate = new Book();
        bookForUpdate.setId(1l);
        bookForUpdate.setTitle("Harry Potter - 3");
        bookForUpdate.setPrice(BigDecimal.valueOf(99.99));
        bookDao.update(bookForUpdate);

        id = 1L;
        bookOnId = bookDao.findById(id);
        if (bookOnId.isEmpty()) {
            System.out.println("Book with id = " + id + " is absent in DB");
        } else {
            System.out.println("Book found with id = " + id + " changed to " + bookOnId.toString());
        }

        id = 100L;
        if (!bookDao.deleteById(id)) {
            System.out.println("No such book with id = " + id + " in the DB");
        } else {
            System.out.println("Book with id = " + id + " has been deleted");
        }

        id = 3L;
        if (!bookDao.deleteById(id)) {
            System.out.println("No such book with id = " + id + " in the DB");
        } else {
            System.out.println("Book with id = " + id + " has been deleted");
        }
    }
}
