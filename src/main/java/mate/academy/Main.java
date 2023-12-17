package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        getBooks().forEach(bookDao::create);
        Book bookFromDbByFirstId = bookDao.findById(1L).get();
        System.out.println("Find by id: \n" + bookFromDbByFirstId + "\n");

        System.out.println("Find all:");
        bookDao.findAll().forEach(System.out::println);

        System.out.println("\nUpdate book :" + bookFromDbByFirstId);
        bookFromDbByFirstId.setTitle("Updated title with first book");
        System.out.println("after updating: " + bookDao.update(bookFromDbByFirstId) + "\n");

        System.out.println("Delete book by id 1L: " + bookDao.deleteById(1L)
                + "\nThe entire list after deleting the first book::");
        bookDao.findAll().forEach(System.out::println);
    }

    private static List<Book> getBooks() {
        return List.of(
                new Book("First book", BigDecimal.valueOf(25.21)),
                new Book("Second book", BigDecimal.valueOf(26.22)),
                new Book("Third book", BigDecimal.valueOf(28.23))
        );
    }
}
