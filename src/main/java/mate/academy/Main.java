package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao dao = (BookDaoImpl) injector.getInstance(BookDao.class);
        Book javaBible = new Book();
        javaBible.setTitle("Java Bible");
        javaBible.setPrice(new BigDecimal(50));
        dao.save(javaBible);

        Book monster = new Book();
        monster.setTitle("Monster");
        monster.setPrice(new BigDecimal(150));
        dao.save(monster);

        Book mathBook = new Book();
        mathBook.setTitle("Math");
        mathBook.setPrice(new BigDecimal(250));
        dao.save(mathBook);

        System.out.println("Saved new book's");
        dao.findAll().forEach(System.out::println);

        System.out.println("Get book with id = " + javaBible.getId());
        System.out.println(dao.findById(javaBible.getId()));

        System.out.println(dao.deleteById(monster.getId())
                ? "book(id = " + monster.getId() + ") is deleted" : "can't delete");
        dao.findAll().forEach(System.out::println);

        Book chessBook = new Book();
        chessBook.setId(mathBook.getId());
        chessBook.setTitle("Chess Tutorial");
        chessBook.setPrice(new BigDecimal(1488));
        dao.update(chessBook);
        System.out.println("Update book with id = " + mathBook.getId());
        dao.findAll().forEach(System.out::println);
    }
}
