package mate.academy;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.impl.ReaderImpl;

public class Main {
    private static final String SQL_QUERY_FILE_NAME = "src/main/resources/init_db.sql";
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao instance = (BookDao) injector.getInstance(BookDao.class);
        ReaderImpl reader = new ReaderImpl();
        String sqlQuery = reader.readFromFile(SQL_QUERY_FILE_NAME);
        try (PreparedStatement statement =
                    ConnectionUtil.getConnection().prepareStatement(sqlQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create table",e);
        }
        instance.create(new Book(1L,"ZVEROBOY",new BigDecimal("999.99")));
        instance.create(new Book(2L,"SLEDOPIT",new BigDecimal("45.99")));
        Book book = new Book(3L, "INDEEC", new BigDecimal("9.39"));
        instance.create(book);
        book.setTitle("DON KIHOT");
        book.setPrice(new BigDecimal("100.10"));
        instance.update(book);
        System.out.println(instance.findAll());
        System.out.println(instance.findById(2L));
        instance.deleteById(2L);
        System.out.println(instance.findAll());
    }
}
