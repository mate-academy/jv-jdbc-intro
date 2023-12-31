package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = new ArrayList<>();
        books.add(new Book("War and Peace", new BigDecimal(1200)));
        books.add(new Book("Inferno", new BigDecimal(1300)));
        books.add(new Book("Harry Potter", new BigDecimal(1500)));

        for (Book book : books) {
            bookDao.create(book);
        }
        Book book = bookDao.findById(1L).orElseThrow(() ->
                new RuntimeException("Value is not present"));

        List<Book> listBooks = bookDao.findAllById();
        System.out.println(listBooks);

        book.setPrice(new BigDecimal(1700));
        Book book1 = bookDao.update(book);
        System.out.println(book1);

        System.out.println(bookDao.deleteById(1L));
    }
}

