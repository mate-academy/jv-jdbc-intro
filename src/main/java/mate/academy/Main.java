package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        //create 2 books
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
        Book book1 = new Book();
        book1.setTitle("book1");
        book1.setPrice(BigDecimal.valueOf(111L));
        book1 = bookDao.create(book1);
        Book book2 = new Book();
        book2.setTitle("book2");
        book2.setPrice(BigDecimal.valueOf(222L));
        book2 = bookDao.create(book2);
        //update second book
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
        book2.setTitle("New title 2");
        book2 = bookDao.update(book2);
        //delete second book
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
        bookDao.deleteById(book2.getId());
        //get first book by id
        Optional<Book> book1Optional = bookDao.findById(book1.getId());
        System.out.println(book1Optional.get());
        Optional<Book> book2Optional = bookDao.findById(book2.getId());
        //expected NoSuchElementException
        book2Optional.get();
    }
}
