package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Pineapple in the mountains.");
        book1.setPrice(new BigDecimal("800.5"));
        Book book2 = new Book();
        book2.setTitle("Schumacher: The Official Inside Story of the Formula One Icon");
        book2.setPrice(new BigDecimal("450"));
        Book book3 = new Book();
        book3.setTitle("Twenty Thousand Leagues Under the Sea");
        book3.setPrice(new BigDecimal("500"));

        Book createdBook1 = bookDao.create(book1);
        Book createdBook2 = bookDao.create(book2);
        Book createdBook3 = bookDao.create(book3);

        Optional<Book> bookById = bookDao.findById(2L);

        List<Book> allBooks = bookDao.findAll();

        Book book4 = new Book();
        book4.setId(3L);
        book4.setTitle("New title");
        book4.setPrice(new BigDecimal("444"));
        Book updatedBook = bookDao.update(book4);

        boolean isBookDeleted = bookDao.deleteById(2L);
    }
}
