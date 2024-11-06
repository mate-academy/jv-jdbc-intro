package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Mate Java");
        book.setPrice(BigDecimal.valueOf(39.99));

        Book book2 = bookDao.create(book);
        System.out.println(book2);

        Optional<Book> bookOptional = bookDao.findById(book2.getId());
        bookOptional.ifPresent(foundBook -> {
            System.out.println("Book found:");
            System.out.println("Title: " + foundBook.getTitle());
            System.out.println("Price: " + foundBook.getPrice());
        });

        List<Book> books = bookDao.findAll();
        System.out.println(books);

        book2.setPrice(BigDecimal.valueOf(50.99));

        Book book3 = bookDao.update(book2);
        System.out.println(book3);

        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println(isDeleted);

        Optional<Book> afterDeletion = bookDao.findById(book.getId());
        System.out.println(afterDeletion.isPresent());

    }
}
