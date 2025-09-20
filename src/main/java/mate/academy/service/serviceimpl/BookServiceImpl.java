package mate.academy.service.serviceimpl;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.models.Book;
import mate.academy.service.BookService;

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
    public Book findById(Long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new DataProcessingException("Can`t find book by ID: "));
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
    public boolean delete(Long id) {
        return bookDao.deleteById(id);
    }
}
