package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {

    /**
     * Створює новий запис книги в базі даних.
     *
     * @param book Книга, яку потрібно створити.
     */
    void create(Book book);

    /**
     * Знаходить всі книги у базі даних.
     *
     * @return Список всіх книг.
     */
    List<Book> findAll();

    /**
     * Видаляє запис про книгу з бази даних за заданим ідентифікатором.
     *
     * @param id Ідентифікатор книги для видалення.
     * @return {@code true}, якщо видалення вдалося, {@code false} - якщо ні.
     */
    boolean deleteById(Long id);

    /**
     * Оновлює інформацію про книгу в базі даних.
     *
     * @param book Нові дані для оновлення.
     * @return Оновлена книга.
     */
    Book update(Book book);

    /**
     * Знаходить книгу в базі даних за заданим ідентифікатором.
     *
     * @param id Ідентифікатор книги для пошуку.
     * @return Об'єкт Optional, який містить знайдену книгу або пустий, якщо книга не знайдена.
     */
    public Optional<Book> findById(Long id);
}
