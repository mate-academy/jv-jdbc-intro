package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(300));
        book.setTitle("Golden rabbit");
        book.setId(4L);
        StringBuilder stringBuilder = new StringBuilder();
        Book createdBook = bookDao.create(book);
        stringBuilder.append(createdBook);
        stringBuilder.append(System.lineSeparator());
        Optional<Book> findById = bookDao.findById(2L);
        stringBuilder.append(findById);
        stringBuilder.append(System.lineSeparator());
        Book updated = bookDao.update(book);
        stringBuilder.append(updated);
        stringBuilder.append(System.lineSeparator());
        List<Book> allBooks = bookDao.findAll();
        stringBuilder.append(allBooks);
        stringBuilder.append(System.lineSeparator());
        boolean deleted = bookDao.deleteById(10L);
        stringBuilder.append(deleted);
        System.out.println(stringBuilder);
    }
}
