package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("main.academy.dao");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.get(1L));

        Book book = new Book();
        book.setId(3L);
        book.setYear(1800);
        book.setQuantity(510);
        book.setName("Siiuuuu");
        book.setAuthor("N1k");
        Book updatedBook = bookDao.save(book);
        System.out.println(updatedBook);


    }
}
