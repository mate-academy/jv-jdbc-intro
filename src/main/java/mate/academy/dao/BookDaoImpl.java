package mate.academy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private List<Book> books = new ArrayList<>();
    private Long idCounter = 1L;

    @Override
    public Book create(Book book) {
        book.setId(idCounter++);
        books.add(book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public Book update(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
                return book;
            }
        }
        throw new IllegalArgumentException("Book not found: " + book.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}
