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
        // create new book
        Book createBook = new Book();
        createBook.setTitle("Create new book");
        createBook.setPrice(new BigDecimal(100));
        Book createResult = bookDao.create(createBook);
        System.out.println("result of create(): " + createResult);
        // find book by id
        Optional<Book> optionalBook = bookDao.findById(3L);
        optionalBook.ifPresent(book -> System.out.println("result of findById(): " + book));
        // update the book
        createBook.setTitle("New Title");
        Book updateResult = bookDao.update(createBook);
        System.out.println("result of update(): " + updateResult);
        // find all books
        List<Book> books = bookDao.findAll();
        System.out.println("All the books");
        books.forEach(System.out::println);
        // delete the book
        bookDao.deleteById(updateResult.getId()); 
        System.out.println("All the after deleting books");
        books = bookDao.findAll();
        books.forEach(System.out::println);
    }
}
