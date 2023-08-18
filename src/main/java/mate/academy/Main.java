package mate.academy;

import java.math.BigDecimal;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book onePiece = new Book();
        onePiece.setTitle("One Piece");
        onePiece.setPrice(BigDecimal.valueOf(1000));
        Book naruto = new Book();
        naruto.setTitle("Naruto");
        naruto.setPrice(BigDecimal.valueOf(500));

        bookDao.create(onePiece);
        bookDao.create(naruto);
        onePiece.setPrice(BigDecimal.valueOf(2000));
        bookDao.update(onePiece);

        System.out.println(bookDao.findById(onePiece.getId()));
        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.deleteById(naruto.getId()));
    }
}
