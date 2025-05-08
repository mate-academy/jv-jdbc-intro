package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        List<Book> books = List.of(
               new Book("OnePiece", new BigDecimal(195)),
               new Book("Naruto", new BigDecimal(235)),
               new Book("Jujutsu Kaisen", new BigDecimal(243))
        );

        books.forEach(bookDao::create);

        Book book1Update = new Book(1L, "OnePiece", new BigDecimal(250));
        Book updatedBook = bookDao.update(book1Update);
        System.out.println("Updated book: " + updatedBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books:");
        allBooks.forEach(System.out::println);

        Optional<Book> bookById = bookDao.findById(1L);
        System.out.println("Book found by id: " + bookById.orElse(null));

        bookDao.deleteById(3L);

        List<Book> listAfterDelete = bookDao.findAll();
        System.out.println("List after delete by id:");
        listAfterDelete.forEach(System.out::println);
    }
}
