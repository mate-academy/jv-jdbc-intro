package mate.academy.db.dao;

public class SqlQueries {
    public static final String CREATE_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    public static final String GET_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    public static final String GET_ALL_BOOKS = "SELECT * FROM books";
    public static final String UPDATE_BOOK = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    public static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";
}
