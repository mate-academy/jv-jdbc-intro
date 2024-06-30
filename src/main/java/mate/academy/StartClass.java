package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.service.BookService;
import mate.academy.service.BookServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StartClass {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        BookService bookService = new BookServiceImpl(bookDao);
        /*
        bookService.create();//C
        bookService.readID();//R
        bookService.readAll();
        bookService.update();//U
        bookService.delete();//D
         */
        //------------------------
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                    "root", "root");
            String initTable = "src/main/resources/init_db.sql";
            List<String> lines = Files.readAllLines(Path.of(initTable));
            String sqlCodeForTable = lines.stream()
                    .collect(Collectors.joining(" "));
            PreparedStatement statement = connection.prepareStatement(sqlCodeForTable);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
