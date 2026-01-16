package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.dao");
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(300));
        book.setTitle("Golden rabbit");
        book.setId(4L);
        Book createdBook = bookDao.create(book);
        Optional<Book> findById = bookDao.findById(2L);
        Book updated = bookDao.update(book);
        boolean deleted = bookDao.deleteById(10L);
        stringBuilder.append(createdBook);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(findById);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(updated);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(deleted);
        System.out.println(stringBuilder);
    }
}
