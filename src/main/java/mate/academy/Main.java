package mate.academy;

import mate.academy.dao.service.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // initialize field values using setters or constructor
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Cartographia: Mapping Civilizations " ,
                BigDecimal.valueOf(35.00));
        Book book2 = new Book("Castles Around the Baltic Sea" ,
                BigDecimal.valueOf(50.00));
        Book book3 = new Book("International Telecommunications Law " +
                "- Second Edition", BigDecimal.valueOf(365.00));

        // test other methods from BookDao
        bookDao.create(book);
        bookDao.create(book3);

        Book book8 = new Book("The earth", BigDecimal.valueOf(250));
        Book createdBook = bookDao.create(book8);
        long id = createdBook.getId();
        createdBook.setTitle("THE EARTH");
        createdBook.setPrice(BigDecimal.valueOf(300));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println(updatedBook);

        bookDao.deleteById(3L);

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        Optional<Book> findBookById = bookDao.findById(1L);
        System.out.println("findBookById = " + findBookById);
    }
}
