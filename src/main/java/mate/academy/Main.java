package mate.academy;

import java.io.File;
import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        File createTableScript = new File("src/main/resources/init_db.sql");
        ScriptCreateTable scriptCreateTable = new ScriptCreateTable();
        String sqlCreateTable = scriptCreateTable.loadSqlScriptFromFile(createTableScript);
        scriptCreateTable.executeCreateTableQuery(sqlCreateTable);

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book footprintsOnTheRoad = new Book("Сліди на дорозі", BigDecimal.valueOf(500));
        Book cleanCode = new Book("Clean Code", BigDecimal.valueOf(700));
        Book effectiveJava = new Book("Effective Java", BigDecimal.valueOf(900));
        bookDao.create(footprintsOnTheRoad);
        bookDao.create(cleanCode);
        bookDao.create(effectiveJava);

        System.out.println(bookDao.findById(cleanCode.getId()));

        cleanCode.setPrice(BigDecimal.valueOf(800));
        bookDao.update(cleanCode);
        System.out.println(bookDao.findById(cleanCode.getId()));

        System.out.println(bookDao.findAll());

        System.out.println(bookDao.deleteById(effectiveJava.getId()));

    }
}
