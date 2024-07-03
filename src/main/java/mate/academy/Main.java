package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("java");
        book1.setPrice(BigDecimal.valueOf(10));
        Book book2 = new Book();
        book2.setTitle("book2");
        book2.setPrice(BigDecimal.valueOf(20));
        Book book3 = new Book();
        book3.setTitle("book3");
        book3.setPrice(BigDecimal.valueOf(30));
        Book book4 = new Book();
        book4.setTitle("book4");
        book4.setPrice(BigDecimal.valueOf(40));
        Book book5 = new Book();
        book5.setTitle("book5");
        book5.setPrice(BigDecimal.valueOf(50));
        // initialize field values using setters or constructor
        //bookDao.create(book1);
        //bookDao.create(book2);
        //bookDao.deleteById(2L);
        //List<Book> books = bookDao.findAll();
        //System.out.println(books);
        //Optional<Book> byId3 = bookDao.findById(4L);
        //System.out.println(byId3);
        //bookDao.create(book4);
        System.out.println(book4);
        book4.setId(5L);
        book4.setPrice(BigDecimal.valueOf(1000));
        book4.setTitle("Java_book");
        bookDao.update(book4);
        Book updatedBook4 = bookDao.findById(5L).get();
        System.out.println(updatedBook4);
    }
}
