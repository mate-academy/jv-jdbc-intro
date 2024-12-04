package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

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
        findByIdAndPrint(book1.getId(), bookDao);
        findByIdAndPrint(book2.getId(), bookDao);
        findByIdAndPrint(book3.getId(), bookDao);
        System.out.println();

        System.out.println("------------= FIND ALL =------------");
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
    }

    private static void findByIdAndPrint(Long id, BookDao bookDao) {
        Book foundBook = bookDao.findById(id).orElseThrow(
                () -> new DataProcessingException("Can't find book with id=" + id));
        System.out.println("Book with id=" + id + ": " + foundBook);
    }
}
