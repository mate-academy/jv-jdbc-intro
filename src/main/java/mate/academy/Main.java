package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        //create
        Book book = new Book();
        book.setTitle("Born to run");
        book.setPrice(new BigDecimal(600));
        Book bookFromDB = bookDao.create(book);
        System.out.println(bookFromDB);

        //get all books
        List<Book> allBooks = bookDao.findAll();
        for (Book b : allBooks) {
            System.out.println(b);
        }

        //find by id
        long id = 15;
        Optional<Book> bookById = bookDao.findById(id);
        bookById.ifPresent(book1 -> {
            System.out.println("Знайдена за id книга: " + id + " " + book1);
        });

        //delete book by id
        long id2 = 13;
        System.out.println(bookDao.deleteById(id2));

        //update book by id
        Book book2 = new Book();
        book.setTitle("Astronomy");
        book.setPrice(new BigDecimal(1000));
        long id1 = 14;
        book.setId(id1);
        Book updatedBook = bookDao.update(book2);
        System.out.println(updatedBook);

    }
}
