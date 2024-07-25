package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;
import mate.academy.service.CreatingTableService;
import mate.academy.service.DataChangeService;
import mate.academy.service.impl.CreatingTableServiceImpl;
import mate.academy.service.impl.DataChangeServiceImpl;

public class Main {
    private static final Book NEW_BOOK = new Book("title", BigDecimal.valueOf(12.99));
    private static final Book ANOTHER_NEW_BOOK = new Book(
            "Second title", BigDecimal.valueOf(15.99));
    private static final Book ANOTHER_NEW_BOOK_UPDATE = new Book(
            2L,"Updated second title", BigDecimal.valueOf(11.00)
    );
    private static final Book THIRD_BOOK = new Book(
            "It's a third book", BigDecimal.valueOf(10.00)
    );

    public static void main(String[] args) {
        CreatingTableService tableService = new CreatingTableServiceImpl();
        tableService.createTable();

        DataChangeService dataChange = new DataChangeServiceImpl();
        dataChange.create(NEW_BOOK);

        dataChange.create(ANOTHER_NEW_BOOK);
        Long idSecondBook = ANOTHER_NEW_BOOK.getId();
        Optional<Book> secondBookById = dataChange.findById(idSecondBook);
        System.out.println(secondBookById.get());
        dataChange.update(ANOTHER_NEW_BOOK_UPDATE);
        Optional<Book> updatedSecondBook = dataChange.findById(idSecondBook);
        System.out.println(updatedSecondBook.get());

        dataChange.create(THIRD_BOOK);
        Long thirdBookId = THIRD_BOOK.getId();
        dataChange.deleteById(thirdBookId);

        List<Book> allBooks = dataChange.findAll();
        System.out.println(allBooks);
    }
}
