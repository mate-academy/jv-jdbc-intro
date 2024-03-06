package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookCreated = bookDao.create(new Book("The Chemist", BigDecimal.valueOf(399)));
        System.out.println("Successful created book: " + bookCreated);
        System.out.println(bookDao.create(new Book("The Host", BigDecimal.valueOf(129))));
        System.out.println("Find by id: " + bookDao.findById(2L));
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);
        System.out.println("Updated book: "
                + bookDao.update(new Book(2L, "Renegades", BigDecimal.valueOf(500))));
        System.out.println("Book with id = " + bookCreated.getId() + " was successful deleted: "
                + bookDao.deleteById(bookCreated.getId()));
        System.out.println("After deleted: " + bookDao.findAll());
    }
}
