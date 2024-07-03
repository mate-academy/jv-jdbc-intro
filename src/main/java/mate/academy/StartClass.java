package mate.academy;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.academy.dao.BookDao;
import mate.academy.dao.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class StartClass {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        initializeDatabase();

        final Long secondBookId = 2L;
        final Long thirdBookId = 3L;
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = createBook();
        Book newBook = new Book(2L,"okok", BigDecimal.valueOf(420));

        bookDao.create(books.get(0));//C
        bookDao.create(books.get(1));
        bookDao.create(books.get(2));
        bookDao.create(books.get(3));
        bookDao.create(books.get(4));

        Optional<Book> book = bookDao.findById(secondBookId);//R

        boolean delete = bookDao.deleteById(thirdBookId);//D

        Book update = bookDao.update(newBook);//U

        List<Book> listOfBooks = bookDao.findAll();

        System.out.println("We have " + book
                + ", we delete third book? -> " + delete
                + " and we updated this book -> " + update);
        for (int i = 0; i < listOfBooks.size(); i++) {
            System.out.println("Our " + i + "th book: " + listOfBooks.get(i));
        }
    }

    private static void initializeDatabase() {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            Path sqlPath = Path.of("src/main/init_db.sql");
            if (!Files.exists(sqlPath)) {
                throw new RuntimeException("Database initialization file 'init_db.sql' not found.");
            }
            List<String> lines = Files.readAllLines(sqlPath);
            String sql = lines.stream()
                    .collect(Collectors.joining(" "));
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private static List<Book> createBook() {
        final int amountOfBooks = 5;
        String title = "Java";
        BigDecimal price = BigDecimal.valueOf(12.32);
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < amountOfBooks; i++) {
            books.add(new Book(title + i,
                    price.add(BigDecimal.valueOf(i))));
        }
        return books;
    }
}
