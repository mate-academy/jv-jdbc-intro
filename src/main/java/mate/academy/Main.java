package mate.academy;

import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.get(1L));

        Book book = new Book();
        book.setId(3L);
        book.setYear(1800);
        book.setQuantity(510);
        book.setName("Siiuuuu");
        book.setAuthor("N1k");
        book.setPrice(200);
        Book updatedBook = bookDao.save(book);
        System.out.println(updatedBook);
        Optional<Book> foundBook = bookDao.findById(1L);
        System.out.println(foundBook);
        Book update = bookDao.update(book);
        System.out.println(update);
        boolean deleted = bookDao.delete(book);
        System.out.println(deleted);

    }
}
