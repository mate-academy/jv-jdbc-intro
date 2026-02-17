package mate.academy.dao;

import mate.academy.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    // CRUD
    //- Book create(Book book);
    //- Optional<Book> findById(Long id);
    //- List<Book> findAll();
    //- Book update(Book book);
    //- boolean deleteById(Long id);

    public Book create (Book book);

    public Optional<Book> findById(Long id);

    public List<Book> findAll();

    public Book update(Book book);

    public boolean deleteById(Long id);
}
