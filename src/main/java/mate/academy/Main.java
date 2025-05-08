package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Long FIND_BY_ID = 2L;
    private static final Long DELETE_BY_ID = 2L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookFirst = new Book("book1", BigDecimal.valueOf(13));
        Book bookSecond = new Book("book2", BigDecimal.valueOf(14));
        Book bookThird = new Book("book3", BigDecimal.valueOf(15));

        bookDao.create(bookFirst);
        bookDao.create(bookSecond);
        bookDao.create(bookThird);

        Optional<Book> foundBook = bookDao.findById(FIND_BY_ID);
        System.out.println((foundBook.map(book -> ("Found book by id: " + book))
                .orElse("Book not found with id " + FIND_BY_ID)));
        System.out.println("All book from DB :" + bookDao.findAll());
        bookSecond.setTitle("changed title");
        bookSecond.setPrice(BigDecimal.valueOf(33));
        System.out.println("Updated book: " + bookDao.update(bookSecond));
        System.out.println("Delete book: " + bookDao.deleteById(2L));
    }
}
