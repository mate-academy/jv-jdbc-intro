package mate.academy.db.dao;

public class SqlQueries {
    public static final String createBook = "INSERT INTO books (title, price) VALUES (?, ?)";
    public static final String getBookById = "SELECT * FROM books WHERE id = ?";
    public static final String getAllBooks = "SELECT * FROM books";
    public static final String updateBook = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    public static final String deleteBook = "DELETE FROM books WHERE id = ?";
}
