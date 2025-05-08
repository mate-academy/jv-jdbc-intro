package mate.academy.model;

public class Book {
    private long id;
    private String title;
    private int price;

    public Book(long id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Book() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id
                + ", title='" + title + '\''
                + ", price=" + price + '}';
    }
}
