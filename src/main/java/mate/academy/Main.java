package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(500));
        book1.setTitle("Minecraft");
        System.out.println(bookDao.create(book1));
        Book book2 = new Book();
        book2.setPrice(BigDecimal.valueOf(700));
        book2.setTitle("I am four");
        Book book3 = new Book();
        book3.setPrice(BigDecimal.valueOf(1000));
        book3.setTitle("THIS");
        System.out.println(bookDao.create(book2));
        System.out.println(bookDao.create(book3));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(35L));
        System.out.println(bookDao.update(book1));
        System.out.println(bookDao.deleteById(33L));
    }
}
