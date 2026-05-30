package mate.academy;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book create(Book book);
    Optional<Book> findbyId(Long id);
    List<Book> findAll();
    Book update(Book book);
    boolean deleteById(Long id);
}
