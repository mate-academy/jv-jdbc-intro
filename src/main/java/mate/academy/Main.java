package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("SecondOne");
        book.setPrice(BigDecimal.valueOf(100));
        bookDao.create(book);
        Optional<Book> bookById = bookDao.findById(5L);
        Book updateBook = bookById.get();
        updateBook.setPrice(BigDecimal.valueOf(111));
        bookDao.update(updateBook);
        System.out.println(bookDao.deleteById(3L));
        List<Book> list = bookDao.findAll();
        list.forEach(System.out::println);
    }
}
