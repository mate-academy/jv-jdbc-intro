package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.academy");
    private static final Long BOOK_ID1 = 1L;
    private static final Long BOOK_ID2 = 2L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Map<String, BigDecimal> bookMap = Map.of(
                "The old man and sea", BigDecimal.valueOf(100),
                "Faust", BigDecimal.valueOf(80),
                "A little prince", BigDecimal.valueOf(85),
                "Dorian Gray", BigDecimal.valueOf(118),
                "Quo vadis", BigDecimal.valueOf(70)
        );

        List<Book> books = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> titlePrice : bookMap.entrySet()) {
            Book book = new Book();
            book.setTitle(titlePrice.getKey());
            book.setPrice(titlePrice.getValue());
            books.add(book);
        }

        // create
        for (Book book : books) {
            bookDao.create(book);
        }

        //read
        Book bookFromDbId2 = bookDao.findById(BOOK_ID2).get();
        System.out.println("Book with id 2: " + bookFromDbId2);

        //read all
        List<Book> booksFromDb = bookDao.findAll();
        System.out.println("Books from db: " + booksFromDb);

        //update
        Book bookToUpdateId2 = new Book();
        bookToUpdateId2.setTitle("Faust (English version)");
        bookToUpdateId2.setPrice(BigDecimal.valueOf(105));
        bookToUpdateId2.setId(BOOK_ID2);
        Book updatedBookId2 = bookDao.update(bookToUpdateId2);
        System.out.println("Updated book with id 2: " + updatedBookId2);

        //delete
        bookDao.deleteById(BOOK_ID1);
        List<Book> booksFromDbUpdate = bookDao.findAll();
        System.out.println("Books from db update: " + booksFromDbUpdate);
    }
}
