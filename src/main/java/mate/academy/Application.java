package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Application {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book createBook = new Book();
        createBook.setTitleBook("Експерименти Лейн");
        createBook.setPriceBook(BigDecimal.valueOf(320));
        Book firstAddedBook = bookDao.create(createBook);
        System.out.println(firstAddedBook);
        System.out.println();

        Long idBook = 3L;
        Optional<Book> foundBook = bookDao.findById(idBook);
        System.out.println("Found by index " + idBook + " is " + foundBook + "\n");

        List<Book> books = bookDao.findAll();
        System.out.println("The list of all the books in database:\n" + books + "\n");

        Book afterUpdateBook = new Book(3L, "Evangelion Vol.3", BigDecimal.valueOf(920));
        System.out.println(bookDao.update(afterUpdateBook));
        System.out.println();

        boolean isBookDelete = bookDao.deleteById(2L);
        System.out.println("Has the book on the entered index been deleted? " + isBookDelete);
    }
}
