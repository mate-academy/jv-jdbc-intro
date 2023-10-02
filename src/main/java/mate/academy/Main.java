package mate.academy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        final Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        List<Book> list = new ArrayList<>();
        list.add(new Book("Gone with the Wind", BigDecimal.valueOf(500.0)));
        list.add(new Book("Clean code", BigDecimal.valueOf(700.0)));
        list.add(new Book("The Sea-Wolf", BigDecimal.valueOf(300.0)));
        list.add(new Book("Riding the Bullet", BigDecimal.valueOf(600.0)));

        for (Book book : list) {
            bookDao.create(book);
        }

        List<Book> resultList = bookDao.findAll();
        Book first = bookDao.findById(list.get(0).getId())
                .orElseThrow(() -> new RuntimeException("Can't find book"));

        Book updated = bookDao.update(new Book(list.get(1).getId(), "Thinking in Java",
                BigDecimal.valueOf(750.0)));

        boolean deleted = bookDao.deleteById(list.get(0).getId());
    }
}
