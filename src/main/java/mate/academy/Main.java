package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final List<Book> books = List.of(
            new Book("title1", BigDecimal.valueOf(234)),
            new Book("title2", BigDecimal.valueOf(111)),
            new Book( "title3", BigDecimal.valueOf(212)),
            new Book( "title4", BigDecimal.valueOf(564)),
            new Book( "title5", BigDecimal.valueOf(222)),
            new Book( "title6", BigDecimal.valueOf(1567))

    );

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        //create
        for (Book book : books) {
            bookDao.create(book);
        }

        //update
        Book bookToUpdate = books.get(3);
        bookToUpdate.setPrice(BigDecimal.valueOf(666));
        bookToUpdate.setTitle("Clean Code");
        bookDao.update(bookToUpdate);

        //get by id
        Optional<Book> byId = bookDao.findById(4L);

        //find all
        List<Book> allBooks = bookDao.findAll();

        //delete by id
        boolean deleteById = bookDao.deleteById(2L);
    }
}
