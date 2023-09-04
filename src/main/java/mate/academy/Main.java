package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        Book bookColony = new Book();
        bookColony.setTitle("SpaceColony");
        bookColony.setPrice(BigDecimal.valueOf(777.77));
        bookDao.create(bookColony);

        Long id = 2L;
        Book bookById = bookDao.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find book by id " + id));
        System.out.println(bookById);

        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Kobzar");
        updatedBook.setPrice(BigDecimal.valueOf(150.00));
        bookDao.update(updatedBook);

        System.out.println(bookDao.deleteById(4L));
    }

}
