package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookOne = new Book(1L, "FirstBookTitle", BigDecimal.valueOf(100));
        Book bookTwo = new Book(2L, "SecondBookTitle", BigDecimal.valueOf(50));
        Book bookThree = new Book(3L, "ThirdBookTitle", BigDecimal.valueOf(150));
        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        bookDao.create(bookThree);
        System.out.println("Books " + bookOne + " " + bookTwo + " " + bookThree + " were created");
        bookThree.setPrice(BigDecimal.valueOf(200));
        bookDao.update(bookThree);
        System.out.println("Book " + bookThree + " was updated");
        System.out.println("Book " + bookTwo + " was deleted"
                + bookDao.deleteById(bookTwo.getId()));
        Optional<Book> optionalBook = bookDao.findById(bookOne.getId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.println("Book found: " + book);
        } else {
            System.out.println("Book with id " + bookOne.getId() + " not found");
        }
        System.out.println(bookDao.findAll());
    }
}
