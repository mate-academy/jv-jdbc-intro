package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.util.DdlScriptRunnerUtil;

public class Main {
    private static final String DDL_SCRIPT_FILE_NAME = "init_db.sql";
    private static final long BOOK_PRICE = 1000L;
    private static final Configuration config = Configuration.getInstance();
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Best book ever!");
        if (config.isRunDdlScript()) {
            DdlScriptRunnerUtil.runScript(DDL_SCRIPT_FILE_NAME);
        }
        // initialize field values using setters or constructor
        for (int i = 1; i < 5; i++) {
            book = bookDao.create(book);
            book.setPrice(BigDecimal.valueOf(BOOK_PRICE * i));
            bookDao.update(book);
        }
        Book bookById = bookDao.findById(1L)
                .orElseThrow(() -> new RuntimeException("Can't find book record with id"));
        System.out.println(bookById);
        bookDao.deleteById(1L);
        List<Book> all = bookDao.findAll();
        all.forEach(System.out::println);
    }
}
