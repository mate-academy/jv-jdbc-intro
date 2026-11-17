package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(new BigDecimal("350.00"));
        Book createdBook = bookDao.create(book);
        System.out.println("Created book ID: " + createdBook.getId());
        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        if (foundBook.isPresent()) {
            System.out.println("Found book: "
                    + foundBook.get().getTitle());
        }
        List<Book> books = bookDao.findAll();
        for (Book currentBook : books) {
            System.out.println("ID: " + currentBook.getId()
                    + ", title: " + currentBook.getTitle()
                    + ", price: " + currentBook.getPrice());
        }
        createdBook.setTitle("Harry Potter Updated");
        createdBook.setPrice(new BigDecimal("450.00"));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook.getTitle());
        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Book deleted: " + isDeleted);
    }
}
