package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    public Book create(Book book);

    public Optional<Book> findById(Long id);

    public List<Book> findAll();

    public Book update(Book book);

    public boolean deleteById(Long id);
}
