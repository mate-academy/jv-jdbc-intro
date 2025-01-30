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
        Book book = new Book();

        book.setTitle("Pirates");
        book.setPrice(BigDecimal.valueOf(30));
        Book createdBook = bookDao.create(book);
        System.out.println("Created book is: " + createdBook);

        Optional<Book> bookById = bookDao.findById(createdBook.getId());
        System.out.println("Book by id is: " + bookById);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books are: " + allBooks);

        createdBook.setTitle("Pirates of Caribian sea");
        createdBook.setPrice(BigDecimal.valueOf(50));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book is: " + updatedBook);

        boolean isDeletedBook = bookDao.deleteById(updatedBook.getId());
        System.out.println("Book was deleted: " + isDeletedBook);
    }
}
