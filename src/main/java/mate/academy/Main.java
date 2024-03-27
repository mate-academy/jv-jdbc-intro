package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        addTestBooksToTheDB(10);
        Book bookInDB = bookDao.findById(4L).get();
        System.out.println(bookInDB);
        Book updatedBookInDB = updateBook(bookInDB, "Harry Potter 1", 875);
        System.out.println(updatedBookInDB);
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        bookDao.deleteById(2L);
    }

    private static void addTestBooksToTheDB(int quantity) {
        Book book = new Book();
        for (int i = 0; i < quantity; i++) {
            book.setTitle("Harry Potter. Part " + (i + 1));
            book.setPrice(BigDecimal.valueOf(500));
            bookDao.create(book);
        }
    }

    private static Book updateBook(Book book, String title, int price) {
        book.setTitle(title);
        book.setPrice(BigDecimal.valueOf(price));
        return bookDao.update(book);
    }
}
