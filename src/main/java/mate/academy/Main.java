package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        for (int i = 1; i < 8; i++) {
            Book randomBook = new Book();
            randomBook.setTitle("Harry Potter " + i);
            randomBook.setPrice(BigDecimal.valueOf(100.99 + i));
            bookDao.create(randomBook);
        }

        Book bookToUpdate = new Book();
        bookToUpdate.setId(3L);
        bookToUpdate.setTitle("Kolobok");
        bookToUpdate.setPrice(BigDecimal.valueOf(99.99));
        bookDao.update(bookToUpdate);

        Optional<Book> bookByIdThree = bookDao.findById(3L);
        bookByIdThree.ifPresent(System.out::println);

        boolean deleteByIdOne = bookDao.deleteById(5L);
        System.out.println(deleteByIdOne);

        List<Book> allBooks = bookDao.findAll();
        for (Book book : allBooks) {
            System.out.println(book);
        }
    }
}
