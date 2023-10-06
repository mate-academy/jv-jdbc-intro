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
        List<Book> books = List.of(
                new Book("Book1", BigDecimal.valueOf(200)),
                new Book("Book2", BigDecimal.valueOf(150)),
                new Book("Book3", BigDecimal.valueOf(250)),
                new Book("Book4", BigDecimal.valueOf(310)),
                new Book("Book5", BigDecimal.valueOf(390)),
                new Book("Book6", BigDecimal.valueOf(400)),
                new Book("Book7", BigDecimal.valueOf(400))
        );
        for (Book b : books) {
            bookDao.create(b);
        }
        Book book1 = books.get(2);
        Optional<Book> findById = bookDao.findById(book1.getId());
        System.out.println(findById);
        List<Book> bookList = bookDao.findAll();
        bookList.stream().forEach(System.out::println);
        Book updateBook = bookDao.update(new Book(4L, "Book444", BigDecimal.valueOf(1000)));
        System.out.println(updateBook);
        boolean deletedBook = bookDao.deleteById(5L);
        System.out.println(deletedBook);
    }
}
