package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book marquezBook = new Book("One Hundred Years of Solitude", new BigDecimal(571));
        Book rowlingBook = new Book("Harry Potter and the Half-Blood Prince", new BigDecimal(250));
        Book secondMarquezBook = new Book(2L, "No One Writes to the Colonel", new BigDecimal(450));

//        Book bookUpdated = bookDao.create(marquezBook);
//        Book bookUpdated2 = bookDao.create(rowlingBook);
//        System.out.println(bookUpdated);
//        System.out.println(bookUpdated2);
//
//        Optional<Book> bookById = bookDao.findById(4L);
//        System.out.println(bookById);
//
//        List<Book> allBooks = bookDao.findAll();
//        for (Book element : allBooks) {
//            System.out.println(element);
//        }
//
//        Book updateBook = bookDao.update(secondMarquezBook);
//        System.out.println(updateBook);

        boolean deleteBook = bookDao.deleteById(1L);
        System.out.println(deleteBook);
    }
}
