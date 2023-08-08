package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book EffectiveJava = new Book();
        Book CleanCode = new Book();
        EffectiveJava.setTitle("Java: Effective programming 3th ed");
        EffectiveJava.setPrice(new BigDecimal(15));
        CleanCode.setTitle("Clean code: A Handbook of Agile Software Craftsmanship");
        CleanCode.setPrice(new BigDecimal(10));

        Book createdEffectiveJavaBook = bookDao.create(EffectiveJava);
        Book createdCleanCodeBook = bookDao.create(CleanCode);
        System.out.println("-----CREATING-----");
        System.out.println(createdEffectiveJavaBook);
        System.out.println(createdCleanCodeBook);
        System.out.println(LINE_SEPARATOR + "-----FIND BY ID-----");
        System.out.println(bookDao.findById(createdCleanCodeBook.getId()));
        System.out.println(LINE_SEPARATOR + "-----FIND ALL-----");
        bookDao.findAll().forEach(System.out::println);
        System.out.println(LINE_SEPARATOR + "-----UPDATE-----");
        System.out.println("Before update: " + createdEffectiveJavaBook);
        createdEffectiveJavaBook.setPrice(new BigDecimal(20));
        System.out.println("After update: " + bookDao.update(createdEffectiveJavaBook));
        System.out.println(LINE_SEPARATOR + "-----DELETE-----");
        System.out.println(bookDao.deleteById(createdEffectiveJavaBook.getId()) ?
                createdEffectiveJavaBook.getTitle() + " is successfully deleted"
                : createdEffectiveJavaBook.getTitle() + " is not deleted");
    }
}
