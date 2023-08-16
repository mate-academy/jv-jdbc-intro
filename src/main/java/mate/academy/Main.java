package mate.academy;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Long bookId = 22L;
    private static final Long idForDelete = 25L;
    private static final Long idForUpdate = 22L;
    private static final BigDecimal priceNewBook = BigDecimal.valueOf(115);
    private static final BigDecimal priceUpdatedBook = BigDecimal.valueOf(2222);
    private static final String newBookName = "New Java Book";
    private static final String updatedBookName = "Updated Java Book";

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle(newBookName);
        book.setPrice(priceNewBook);

        Book bookForUpdate = new Book();
        bookForUpdate.setId(idForUpdate);
        bookForUpdate.setTitle(updatedBookName);
        bookForUpdate.setPrice(priceUpdatedBook);

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookCreated = bookDao.create(book);
        System.out.println("***Creat Book***");
        System.out.println(bookCreated);

        Book bookFindById = bookDao.findById(bookId)
                .orElseThrow(() ->
                        new NoSuchElementException("Can't get book by id: " + bookId));
        System.out.println("***Find Book by ID***");
        System.out.println(bookFindById);

        System.out.println("***Get All Books***");
        bookDao.findAll().forEach(System.out::println);

        System.out.println("***Update Book***");
        System.out.println(bookDao.update(bookForUpdate));

        System.out.println("***Get All Books***");
        bookDao.findAll().forEach(System.out::println);

        System.out.println("***Delete Book***");
        System.out.println(bookDao.deleteById(idForDelete));

        System.out.println("***Get All Books***");
        bookDao.findAll().forEach(System.out::println);
    }
}
