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
        List<Book> bookList = getListBook();
        bookDao.create(bookList.get(0));
        Optional<Book> optionalBook = bookDao.findById(1L);
        if (optionalBook.isPresent()) {
            System.out.println(optionalBook.get());
        }
        List<Book> books = bookDao.findAll();
        System.out.println(books);
        bookDao.update(bookList.get(1));
        List<Book> updatedBookList = bookDao.findAll();
        System.out.println(updatedBookList);
        boolean isDelete = bookDao.deleteById(1L);
        System.out.println(isDelete);
    }

    private static List<Book> getListBook() {
        Book book = new Book();
        book.setTitle("1984");
        book.setPrice(new BigDecimal(100));
        Book newBook = new Book();
        newBook.setTitle("365 days");
        newBook.setPrice(new BigDecimal(50));
        newBook.setId(1L);
        List<Book> books = List.of(book, newBook);
        return books;
    }
}
