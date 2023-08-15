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
        for (Book b: allBooks) {
            System.out.println(b);
        }

        //find by id
//        long id = 15;
//        Optional<Book> bookById = bookDao.findById(id);
//        bookById.ifPresent(book -> {
//            System.out.println("Знайдена за id книга: " + id + " " + book);
//        });


        //delete book by id
//        long id = 13;
//        System.out.println(bookDao.deleteById(id));

        //update book by id
//        Book book = new Book();
//        book.setTitle("Astronomy");
//        book.setPrice(new BigDecimal(1000));
//        long id = 14;
//        book.setId(id);
//        Book updatedBook = bookDao.update(book);
//        System.out.println(updatedBook);

    }
}
