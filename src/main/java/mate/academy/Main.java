package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(new Book(1L, "Java Programming", new BigDecimal("1000.00")));
        bookDao.create(new Book(2L, "Effective Java", new BigDecimal("1500.00")));
        bookDao.create(new Book(3L, "Clean Code", new BigDecimal("2000.00")));
        bookDao.create(new Book(4L, "Head First Java", new BigDecimal("2500.00")));
        bookDao.create(new Book(5L, "Java Concurrency in Practice", new BigDecimal("3000.00")));

        System.out.println("FULL list of books");
        List<Book> list = bookDao.findAll();
        for (Book book : list) {
            System.out.println(book.getId() + " " + book.getTitle() + " " + book.getPrice());
        }

        System.out.println("\n" + "Finding book by index:" + list.get(0).getId());
        Book book1 = bookDao.findById(list.get(0).getId())
                .orElseThrow(() ->
                        new RuntimeException("Objects with the specified ID do not exist"));
        System.out.println(book1.getId() + " " + book1.getTitle() + " " + book1.getPrice());

        System.out.println("\n" + "Updating book by index:" + list.get(0).getId());
        bookDao.update(new Book(list.get(0).getId(), "Nikilen", new BigDecimal(100)));

        Book book2 = bookDao.findById(list.get(0).getId())
                .orElseThrow(() ->
                        new RuntimeException("Objects with the specified ID do not exist"));

        System.out.println(book2.getId() + " " + book2.getTitle() + " " + book2.getPrice());

        System.out.println("\n" + "Deleting an object by index:" + list.get(0).getId());

        if (bookDao.deleteById(list.get(0).getId())) {
            System.out.println("The object is deleted");
        } else {
            System.out.println("The object does not exist");
        }
    }
}
