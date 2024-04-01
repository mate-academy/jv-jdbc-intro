package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.db.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(null, "Pride and Prejudice by Jane Austen", new BigDecimal("12.50"));
        bookDao.create(book);
        book = new Book(null, "The Great Gatsby by F. Scott Fitzgerald", new BigDecimal("21.00"));
        bookDao.create(book);

        book = new Book(null, "Nineteen Eighty-Four by George Orwell", new BigDecimal("16.45"));
        book = bookDao.create(book);
        System.out.println("Last added book: " + book);

        book = bookDao.findById(1L).get();
        System.out.println("Found book by id=1: " + book);

        List<Book> bookList = bookDao.findAll();
        System.out.println("List of all book in DB:");
        bookList.forEach(System.out::println);

        book.setPrice(new BigDecimal("14.20"));
        book = bookDao.update(book);
        System.out.println("Updated book with id=1: " + book);
        System.out.println("Updated book equals book in DB: "
                + book.equals(bookDao.findById(1L).get()));

        System.out.println("Deleted book with id=3: " + bookDao.deleteById(3L));
        System.out.println("Finding book with id=3 returns empty Optional: "
                + bookDao.findById(3L).equals(Optional.empty()));
    }
}
