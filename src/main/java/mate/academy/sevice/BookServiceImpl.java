package mate.academy.sevice;

import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.model.Book;

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
    public Book get(Long id) {
        return bookDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Can't find book with id " + id));
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
