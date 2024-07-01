package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StartClass {
    private final static BookDao bookDao = new BookDaoImpl();
    private final static BookService bookService = new BookServiceImpl(bookDao);
    private final static int AMOUNT_OF_BOOKS = 5;
    private final static Long FIRST_BOOK_ID = 1L;
    private final static Long SECOND_BOOK_ID = 2L;
    private final static Long THIRD_BOOK_ID = 3L;
    private final static Long FOURTH_BOOK_ID = 4L;
    private final static Long FIFTH_BOOK_ID = 5L;


    public static void main(String[] args) {
        List<Book> books = createBook();
        Book newBook = new Book("okok", BigDecimal.valueOf(420));

        bookService.create(books.get(0));//C
        bookService.create(books.get(1));
        bookService.create(books.get(2));
        bookService.create(books.get(3));
        bookService.create(books.get(4));

        Optional<Book> book = bookService.readID(SECOND_BOOK_ID);//R

        boolean delete = bookService.delete(THIRD_BOOK_ID);//D

        Book update = bookService.update(newBook);//U

        List<Book> listOfBooks = bookService.readAll();

        System.out.println("We have " + book +
                ", we delete third book? -> " + delete +
                " and we updated this book -> " + update);
        for (int i = 0; i < listOfBooks.size(); i++) {
            System.out.println("Our " + i + "th book: " + listOfBooks.get(i));
        }
    }

    private static List<Book> createBook() {
        String title = "Java";
        BigDecimal price = BigDecimal.valueOf(12.32);
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < AMOUNT_OF_BOOKS; i++) {
            books.add(new Book(title + i,
                    price.add(BigDecimal.valueOf(i))));
        }
        return books;
    }
}
