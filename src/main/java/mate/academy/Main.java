package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book seaWolf = new Book();
        seaWolf.setPrice(new BigDecimal("200.000"));
        seaWolf.setTitle("The Sea Wolf");
        Book metamorphosis = new Book();
        metamorphosis.setTitle("Metamorphosis");
        metamorphosis.setPrice(new BigDecimal("150.000"));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(seaWolf);
        bookDao.create(metamorphosis);
        metamorphosis.setPrice(new BigDecimal("100.000"));
        bookDao.update(metamorphosis);
        List<Book> books = bookDao.findAll();
        if (books.contains(seaWolf) && books.contains(metamorphosis)) {
            System.out.println("All books present");
        } else {
            System.out.println("Required books are not present in list: " + books);
        }
        bookDao.findById(seaWolf.getId()).orElseThrow(() ->
                new RuntimeException("Looked book was not found."));
        if (!(bookDao.delete(seaWolf) && bookDao.delete(metamorphosis)
                && bookDao.findAll().isEmpty())) {
            throw new RuntimeException("Table books is not empty. Books: " + bookDao.findAll());
        }
    }
}
