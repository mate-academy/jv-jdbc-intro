package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.dao.BookDaoImpl;
import mate.academy.lib.model.Book;
import mate.academy.lib.settings.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        createBook();
        listBooks();
        deleteBookById(12L);
        findBookById(14L);
        updateBook(12L);
    }

    private static void createBook() {
        Book newBook = new Book();
        newBook.setTitle("Test");
        newBook.setPrice(new BigDecimal("324"));
        // Use setters or constructor to initialize field values
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book: " + createdBook);
    }

    private static void listBooks() {
        System.out.println("Listing all books:");
        bookDao.findAll().forEach(System.out::println);
    }

    private static void deleteBookById(Long bookId) {
        boolean isDeleted = bookDao.deleteById(bookId);
        System.out.println("Book deletion status for ID " + bookId + ": " + isDeleted);
    }

    private static void findBookById(Long bookId) {
        bookDao.findById(bookId).ifPresentOrElse(
                book -> System.out.println("Found book: " + book),
                () -> System.out.println("Book with ID " + bookId + " not found.")
        );
    }

    private static void updateBook(Long bookId) {
        bookDao.findById(bookId).ifPresentOrElse(
                book -> {
                    book.setTitle("New Title");
                    book.setPrice(new BigDecimal("349094"));
                    Book updatedBook = bookDao.update(book);
                    System.out.println("Updated book: " + updatedBook);
                },
                () -> System.out.println("Book with ID "
                        + bookId + " doesn't exist and cannot be updated.")
        );
    }
}
