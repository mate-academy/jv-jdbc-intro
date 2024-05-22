package mate.academy.service.serviceimpl;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.exeption.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

@Dao
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookDao.findById(id)
                .orElseThrow(() -> new DataProcessingException("Can't find book with id - " + id)));
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id);
    }
}
