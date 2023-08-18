package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book java = new Book();
        java.setBook_id(12);
        java.setAuthor("Ivan M.");
        java.setTitle("java");
        java.setGenre("programing");
        java.setPrice(12);
        java.setPublication_year(1992);

        Book javaBook = bookDao.create(java);
        System.out.println("-----CREATING-----");
        System.out.println(javaBook);

        System.out.println(LINE_SEPARATOR + "-----FIND BY ID-----");
        System.out.println(bookDao.findById(javaBook.getBook_id()));

        System.out.println(LINE_SEPARATOR + "-----FIND ALL-----");
        bookDao.findAll().forEach(System.out::println);

        System.out.println(LINE_SEPARATOR + "-----UPDATE-----");
        System.out.println("Before update: " + javaBook);
        javaBook.setPrice((100));
        System.out.println("After update: " + bookDao.update(javaBook));

        System.out.println(LINE_SEPARATOR + "-----DELETE-----");
        System.out.println("Before delete: ");
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(java.getBook_id());
        System.out.println("After delete: ");
        bookDao.findAll().forEach(System.out::println);
    }
}
