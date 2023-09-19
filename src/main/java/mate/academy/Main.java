package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book("Shantaram", BigDecimal.valueOf(548.60));
        Book secondBook = new Book("The Godfather", BigDecimal.valueOf(350.50));
        Book thirdBook = new Book("The Silmarillion", BigDecimal.valueOf(685.00));
        BookDao bookDao = (BookDaoImpl) INJECTOR.getInstance(BookDao.class);
        //inserting books to DB
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);
        //find by id
        Long findId = 1L;
        Book bookById = bookDao.findById(findId).orElseThrow(()
                -> new RuntimeException("Can't find book by id " + findId));
        System.out.println(bookById);
        //find all
        bookDao.findAll().forEach(System.out::println);
        //update
        Book updateBook = new Book(1L, "Mountain Shadow", BigDecimal.valueOf(599.99));
        System.out.println(bookDao.update(updateBook));
        //delete by id
        Long deleteId = 2L;
        bookDao.deleteById(deleteId);
        bookDao.findAll().forEach(System.out::println);
    }
}
