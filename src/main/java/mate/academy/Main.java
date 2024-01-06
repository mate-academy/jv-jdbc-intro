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
        Book book1 = new Book("Book # 1", BigDecimal.valueOf(100));
        Book createdBook1 = bookDao.create(book1);
        System.out.println(createdBook1);

        Book book2 = new Book("Book # 2", BigDecimal.valueOf(50));
        Book createdBook2 = bookDao.create(book2);
        System.out.println(createdBook2);

        createdBook2.setTitle("New Title for Book #2");
        createdBook2.setPrice(BigDecimal.valueOf(44.99));
        Book updatedBook = bookDao.update(createdBook2);
        System.out.println(updatedBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println(allBooks);

        Optional<Book> findedBook = bookDao.findById(1L);
        System.out.println(findedBook);

        boolean bookDeleted = bookDao.deleteById(1L);
        System.out.println("Book deleted : " + bookDeleted);

        List<Book> allBooksUpdated = bookDao.findAll();
        System.out.println(allBooksUpdated);
    }
}
