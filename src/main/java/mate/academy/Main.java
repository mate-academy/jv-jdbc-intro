package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> booksList = new ArrayList<>();
        booksList.add(new Book("Horse", BigDecimal.valueOf(550)));
        booksList.add(new Book("Frog", BigDecimal.valueOf(650)));
        booksList.add(new Book("Fox", BigDecimal.valueOf(750)));

        booksList.forEach(bookDao::create);

        Book updateBook = bookDao.findById(1L).orElseThrow(() ->
                new DataProcessingException("Can`t found book"));
        updateBook.setTitle("BookAfterUpdate");
        updateBook.setPrice(BigDecimal.valueOf(777));
        bookDao.update(updateBook);
        bookDao.findAll();
        bookDao.deleteById(1L);
    }
}
