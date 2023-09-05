package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // initialisation of books
        Book firstBook = new Book();
        firstBook.setTitle("FirstBook");
        firstBook.setPrice(BigDecimal.valueOf(123.12));

        Book secondBook = new Book();
        secondBook.setTitle("SecondBook");
        secondBook.setPrice(BigDecimal.valueOf(345.34));

        Book thirdBook = new Book();
        thirdBook.setTitle("ThirdBook");
        thirdBook.setPrice(BigDecimal.valueOf(567.56));

        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        // creating books
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        // finding book by id
        Long idToFind = 1L;
        Book bookById = bookDao.findById(idToFind).orElseThrow(
                () -> new RuntimeException("Book not found by id " + idToFind));
        System.out.println(bookById);

        // finding all books
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }

        // book update
        Book updatedBook = new Book();
        updatedBook.setId(2L);
        updatedBook.setTitle("NextBook");
        updatedBook.setPrice(BigDecimal.valueOf(150.00));
        bookDao.update(updatedBook);

        // book deleting
        Long idToDelete = 2L;
        System.out.println(bookDao.deleteById(idToDelete));
    }

}
