package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        //creating books
        Book book = new Book("The clean code", BigDecimal.valueOf(250));
        Book createdBook = bookDao.create(book);
        //getting books by id
        long id = createdBook.getId();
        Book foundBookById = bookDao.findById(id).orElseThrow(() -> new RuntimeException("Can't find a book by id: " + id));
        System.out.println(foundBookById);
        //getting all books
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        //updating books
        createdBook.setTitle("THE CLEAN CODE");
        createdBook.setPrice(BigDecimal.valueOf(300));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println(updatedBook);
        //deleting books
        boolean isDeletedBook = bookDao.deleteById(createdBook.getId());
        System.out.println(isDeletedBook);
    }
}
