package mate.academy.model;

public class Book {
    private long id;
    private String title;
    private int price;

    private Book(long id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    private Book(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public static Book of(long id, String title, int price) {
        return new Book(id, title, price);
    }

    public static Book of(String title, int price) {
        return new Book(title, price);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
