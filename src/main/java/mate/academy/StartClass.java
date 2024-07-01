package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

public class StartClass {
    public static void main(String[] args) {
        final Long secondBookId = 2L;
        final Long thirdBookId = 3L;

        final BookDao bookDao = new BookDaoImpl();
        final BookService bookService = new BookServiceImpl(bookDao);
        List<Book> books = createBook();
        Book newBook = new Book(3L,"okok", BigDecimal.valueOf(420));

        bookService.create(books.get(0));//C
        bookService.create(books.get(1));
        bookService.create(books.get(2));
        bookService.create(books.get(3));
        bookService.create(books.get(4));

        Optional<Book> book = bookService.readID(secondBookId);//R

        boolean delete = bookService.delete(thirdBookId);//D

        Book update = bookService.update(newBook);//U

        List<Book> listOfBooks = bookService.readAll();

        System.out.println("We have " + book
                + ", we delete third book? -> " + delete
                + " and we updated this book -> " + update);
        for (int i = 0; i < listOfBooks.size(); i++) {
            System.out.println("Our " + i + "th book: " + listOfBooks.get(i));
        }
    }

    private static List<Book> createBook() {
        final int amountOfBooks = 5;
        String title = "Java";
        BigDecimal price = BigDecimal.valueOf(12.32);
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < amountOfBooks; i++) {
            books.add(new Book(title + i,
                    price.add(BigDecimal.valueOf(i))));
        }
        return books;
    }
}
