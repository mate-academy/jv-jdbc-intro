package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    //crud
    //create
    Book create(Book book);

    //read
    Book get(Long id);

    Optional<Book> findById(Long id);

    //update
    Book update(Book book);

    //delete
    boolean deleteById(Long id);

    //finally, we can add a method to find all books
    List<Book> findAll();
}
