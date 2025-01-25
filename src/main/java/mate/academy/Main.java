package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String bookTitle = "Prison";
    private static final String bookSecondTitle = "Horizon";
    private static final double price = 200.2;
    private static final double updatedPrice = 300.3;

    public static void main(String[] args) {
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle(bookTitle);
        book.setPrice(BigDecimal.valueOf(price));
        Book updatedBook = bookDao.create(book);
        System.out.println(updatedBook + " is inserted to DB");

        Optional<Book> optionalBook = bookDao.findById(updatedBook.getId());
        if (optionalBook.isPresent()) {
            System.out.println("Book: " + optionalBook.get().getTitle() + " is present in DB");
        }

        book.setTitle(bookSecondTitle);
        bookDao.create(book);
        List<Book> bookList = bookDao.findAll();
        for (Book b : bookList) {
            System.out.println(b);
        }

        book.setPrice(BigDecimal.valueOf(updatedPrice));
        updatedBook = bookDao.update(book);
        System.out.println("New price for book: " + updatedBook.getTitle() + " is "
                + updatedBook.getPrice());

        if (bookDao.deleteById(updatedBook.getId())) {
            System.out.println("Book with id = " + updatedBook.getId() + " was deleted");
        }
    }
}
