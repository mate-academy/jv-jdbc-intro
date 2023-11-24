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

        Book book1 = new Book();
        book1.setTitle("Effective Java");
        book1.setPrice(new BigDecimal(15));
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Refactoring: Improving the Design of Existing Code");
        book2.setPrice(new BigDecimal(20));
        bookDao.create(book2);

        Book book3 = new Book();
        book3.setTitle("Docker: Practical Guide for Developers and Devops Teams");
        book3.setPrice(new BigDecimal(18));
        bookDao.create(book3);

        List<Book> bookList = bookDao.findAll();
        bookList.forEach(System.out::println);

        Optional<Book> bookOptional = bookDao.findById(bookList.get(0).getId());
        if ((bookOptional.isPresent())) {
            System.out.println(bookOptional.get());
        }

        Book book4 = bookOptional.get();
        System.out.println(book4.getPrice());
        book4.setPrice(new BigDecimal(25));
        Book updatedBook = bookDao.update(book4);
        System.out.println(updatedBook.getPrice());

        bookList.forEach(e -> bookDao.deleteById(e.getId()));


        bookDao.findAll().forEach(e -> System.out.println(e));
        boolean b = bookDao.deleteById(book4.getId());
        System.out.println(b);
        bookDao.findAll().forEach(e -> System.out.println(e));
    }
}
