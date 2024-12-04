package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("Some book 1", BigDecimal.valueOf(11.11));
        Book book2 = new Book("Some book 2", BigDecimal.valueOf(22.22));
        Book book3 = new Book("Some book 3", BigDecimal.valueOf(33.33));

        Book createdBook1 = bookDao.create(book1);
        Book createdBook2 = bookDao.create(book2);
        Book createdBook3 = bookDao.create(book3);

        System.out.println("------------= CREATE =------------");
        System.out.println(createdBook1 + " created!");
        System.out.println(createdBook2 + " created!");
        System.out.println(createdBook3 + " created!");
        System.out.println();

        System.out.println("------------= FIND BY ID =------------");
        Long id1 = book1.getId();
        Book foundBook1 = bookDao.findById(id1).orElseThrow(
                () -> new DataProcessingException("Can't find book with id=" + id1));
        System.out.println("Book with id=" + id1 + ": " + foundBook1);
        Long id2 = book1.getId();
        Book foundBook2 = bookDao.findById(id2).orElseThrow(
                () -> new DataProcessingException("Can't find book with id=" + id2));
        System.out.println("Book with id=" + id2 + ": " + foundBook2);
        Long id3 = book1.getId();
        Book foundBook3 = bookDao.findById(id3).orElseThrow(
                () -> new DataProcessingException("Can't find book with id=" + id3));
        System.out.println("Book with id=" + id3 + ": " + foundBook3);
    }
}
