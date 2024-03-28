package mate.academy.service;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.lib.Service;
import mate.academy.model.Book;

@Service
public class BookServiceImpl implements BookService {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    @Override
    public Book create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Book get(Long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new DataProcessingException(
                        "Book with id " + id + " not found"));
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
