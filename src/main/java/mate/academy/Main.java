package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("The White Fang");
        book.setPrice(BigDecimal.valueOf(10));
        BookDao bookDao = new BookDaoImpl();
        System.out.println(bookDao.create(book));
        Book book1 = new Book();
        book1.setId(2L);
        book1.setTitle("The Witcher");
        book1.setPrice(BigDecimal.valueOf(25));
        System.out.println(bookDao.findById(book.getId()));
        List<Book> bookList = bookDao.findAll();
        for (Book b : bookList) {
            System.out.println(b);
        }
        bookDao.update(book1);
        bookDao.deleteById(3L);
    }
}
