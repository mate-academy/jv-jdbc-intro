package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static Book bookBeauty = new Book();
    private static final Book bookPeace = new Book();
    private static final Book bookFun = new Book();
    private static BookDao bookDao;

    static {
        bookBeauty.setTitle("Beauty");
        bookBeauty.setPrice(BigDecimal.valueOf(20.99));
        bookPeace.setTitle("Peace");
        bookPeace.setPrice(BigDecimal.valueOf(100.00));
        bookFun.setTitle("Fun");
        bookFun.setPrice(BigDecimal.valueOf(15.80));
    }

    public static void main(String[] args) {
        bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookBeautyWithId = createBook(bookBeauty);
        System.out.println("Added new book to DB: " + bookBeautyWithId);
        System.out.println("Added new book to DB: " + createBook(bookPeace));
        System.out.println("Added new book to DB: " + createBook(bookFun));
        Long bookBeautyId = bookBeautyWithId.getId();
        Optional<Book> bookById = bookDao.findById(bookBeautyId);
        System.out.println("Found book by id: " + bookBeautyId
                + ". The book is: " + bookById);
        List<Book> allBooks = bookDao.findAll();
        System.out.println("List of all books: ");
        outputAllBooksList(allBooks);
        bookBeautyWithId.setPrice(BigDecimal.valueOf(25.00));
        bookBeautyWithId.setTitle("BEAUTY");
        bookDao.update(bookBeautyWithId);
        System.out.println("Before updating, title: Beauty; price: 20.99. "
                + "After updating should be: title: BEAUTY, price: 25.00. "
                + System.lineSeparator() + "Updated book is: "
                + bookDao.findById(bookBeautyId));
        Long bookFunId = bookFun.getId();
        bookDao.deleteById(bookFunId);
        System.out.println("Deleted book with title \"Fun\", id: " + bookFunId);
        List<Book> booksAfterDelete = bookDao.findAll();
        System.out.println("List of all books after delete: ");
        outputAllBooksList(booksAfterDelete);
    }

    private static Book createBook(Book book) {
        return bookDao.create(bookBeauty);
    }

    private static void outputAllBooksList(List<Book> books) {
        if (!books.isEmpty()) {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}
