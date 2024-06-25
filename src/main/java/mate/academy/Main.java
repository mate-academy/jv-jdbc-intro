package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("Whats the Point of Maths?",
                        BigDecimal.valueOf(200)),
                new Book("Upstream Intermediate Workbook",
                        BigDecimal.valueOf(350)),
                new Book("See Inside Your Body",
                        BigDecimal.valueOf(400)),
                new Book("Henry Ford: My life and My work",
                        BigDecimal.valueOf(250)),
                new Book("GCSE Edexcel Mathematics for the Grade 9-1 Course",
                        BigDecimal.valueOf(350))
        );
        for (Book book : books) {
            bookDao.create(book);
        }
        System.out.println(bookDao.findById(3L));
        System.out.println(bookDao.deleteById(5L));
        System.out.println(bookDao.findAll().toString());
        Book book = books.get(4);
        book.setPrice(BigDecimal.valueOf(225));
        System.out.println(bookDao.update(book));
    }
}
