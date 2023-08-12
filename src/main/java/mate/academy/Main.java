package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book1 = new Book("book1", BigDecimal.valueOf(100));
        Book book2 = new Book("book2", BigDecimal.valueOf(200));
        Book book3 = new Book("book3", BigDecimal.valueOf(300));

        book1 = bookDao.create(book1);
        book2 = bookDao.create(book2);
        book3 = bookDao.create(book3);

        List<Book> books = bookDao.findAll();
        System.out.println("\nAdd books");
        books.forEach(System.out::println);

        Book bookFromList = books.get(1);
        Optional<Book> bookFromDB = bookDao.findById(bookFromList.getId());

        System.out.println("\nFind by id compare books");
        System.out.println(bookFromList);
        System.out.println(bookFromDB);

        Book updateBook = book1;
        updateBook.setTitle("updateBook");
        updateBook.setPrice(BigDecimal.valueOf(150));
        bookDao.update(updateBook);

        System.out.println("\nUpdate book1/newBook");
        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(book2.getId());

        System.out.println("\nDelete book2");
        bookDao.findAll().forEach(System.out::println);
    }
}
