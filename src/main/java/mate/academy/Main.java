package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(207));
        book.setTitle("Sherlock Holmes");
        book.setId(4L);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("Creating book: " + bookDao.create(book));
        System.out.println("Finding book: " + bookDao.findById(4L));
        System.out.println("Deleting book: " + bookDao.deleteById(3L));
        System.out.println("Updating book " + bookDao.update(book));
        List<Book> books = bookDao.findAll();
        for (Book value : books) {
            System.out.println("Book id: " + value.getId());
            System.out.println("Book title: " + value.getTitle());
            System.out.println("Book price: " + value.getPrice());
        }
    }
}
