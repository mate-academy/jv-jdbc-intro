package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("Cinderella");
        firstBook.setPrice(BigDecimal.valueOf(200.00));
        Book secondBook = new Book();
        secondBook.setTitle("Harry Potter");
        secondBook.setPrice(BigDecimal.valueOf(500.00));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("Test 1: add new books to table books");
        Book save = bookDao.create(firstBook);
        System.out.println("added new book: " + save.toString());
        System.out.println("added new book: " + bookDao.create(secondBook).toString());
        System.out.println();
        System.out.println("Test 2: find book by id");
        System.out.println("find book by id 2 " + bookDao.findById(save.getId()));
        System.out.println();
        System.out.println("Test 3: find all books");
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println();
        System.out.println("Test 4: update price of book 'Harry Potter'");
        save.setTitle("Harry Potter");
        save.setPrice(BigDecimal.valueOf(900.00));
        System.out.println("update book by id 1 " + bookDao.update(save));
        System.out.println();
        System.out.println("Test 5: delete book by id 2");
        boolean isSuccess = bookDao.deleteById(save.getId());
        System.out.println("Is delete successful? " + isSuccess);
        System.out.println();
        System.out.println("show database after all manipulations");
        List<Book> booksAfter = bookDao.findAll();
        for (Book book : booksAfter) {
            System.out.println(book);
        }
    }
}
