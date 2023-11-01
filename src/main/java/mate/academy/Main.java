package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

//        Book book = new Book();
//        book.setTitle("Mathematics");
//        BigDecimal price = new BigDecimal("230.00");
//        book.setPrice(price);
//        Book bookCreate = bookDao.create(book);
//        System.out.println(bookCreate);

        List<Book> all = bookDao.findAll();
        System.out.println(all);

        Optional<Book> byId = bookDao.findById(1L);
        byId.ifPresent(System.out::println);

//        Book updateBook = new Book();
//        updateBook.setId(1L);
//        updateBook.setTitle("Geography");
//        updateBook.setPrice(new BigDecimal("245.50"));
//        Book update = bookDao.update(updateBook);
//        System.out.println("The book is update" + update);
//
//        boolean deleteById = bookDao.deleteById(4L);
//        System.out.println(deleteById);
    }
}
