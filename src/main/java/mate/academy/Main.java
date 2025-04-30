package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book("Shantaram", BigDecimal.valueOf(548.60));
        Book secondBook = new Book("The Godfather", BigDecimal.valueOf(350.50));
        Book thirdBook = new Book("The Silmarillion", BigDecimal.valueOf(685.00));
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        //inserting books to DB
        bookDao.create(firstBook);
        Book secondBookFromDB = bookDao.create(secondBook);
        Book thirdBookFromDB = bookDao.create(thirdBook);
        //find by id
        Book bookById = bookDao.findById(secondBookFromDB.getId()).orElseThrow(()
                -> new RuntimeException("Can't find book by id " + firstBook.getId()));
        System.out.println(bookById);
        //find all
        bookDao.findAll().forEach(System.out::println);
        //update
        Book updateBook = new Book(thirdBookFromDB.getId(),
                "Mountain Shadow", BigDecimal.valueOf(599.99));
        System.out.println(bookDao.update(updateBook));
        //delete by id
        bookDao.deleteById(secondBookFromDB.getId());
        bookDao.findAll().forEach(System.out::println);
    }
}
