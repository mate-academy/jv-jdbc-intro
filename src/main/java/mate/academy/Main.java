package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.impl.BookServiceImpl;

public class Main {
    private static final String PACKAGE_NAME = "mate.academy";
    private static final Injector injector = Injector.getInstance(PACKAGE_NAME);

    public static void main(String[] args) {
        var bookDao = (BookDao) injector.getInstance(BookDao.class);
        var bookService = new BookServiceImpl(bookDao);
        var newBook = saveNewBook(bookService);
        getBookById(bookService, newBook);
        updateBook(bookService, newBook);
        getAllBooks(bookService);
        deleteBook(bookService, newBook);
    }

    private static Book saveNewBook(BookService bookService) {
        var newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setPrice(BigDecimal.valueOf(30.99));
        Book savedBook = bookService.save(newBook);
        System.out.println("Saved book: " + savedBook);
        return savedBook;
    }

    private static void getBookById(BookService bookService, Book savedBook) {
        Optional<Book> bookById = bookService.get(savedBook.getId());
        System.out.println("Book by id " + savedBook.getId() + " is: " + bookById.orElse(null));
    }

    private static void updateBook(BookService bookService, Book savedBook) {
        savedBook.setTitle("New Book.Second Edition.");
        savedBook.setPrice(BigDecimal.valueOf(35.99));
        System.out.println("Updated book: " + bookService.update(savedBook));
    }

    private static void getAllBooks(BookService bookService) {
        List<Book> allBooks = bookService.getAll();
        allBooks.forEach(book -> System.out.println("Book: " + book));
    }

    private static void deleteBook(BookServiceImpl bookService, Book savedBook) {
        boolean isDeleted = bookService.delete(savedBook.getId());
        System.out.println("Deleted book with id " + savedBook.getId() + " : " + isDeleted);
    }
}
