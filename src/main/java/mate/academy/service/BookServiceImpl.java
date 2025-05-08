package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book createBook(Book book) {
        return bookDao.create(book);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book updateBook(Long id, Book book) {
        book.setId(id);
        return bookDao.update(book);
    }

    @Override
    public boolean deleteBookById(Long id) {
        return bookDao.deleteById(id);
    }
}
