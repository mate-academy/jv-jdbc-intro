package mate.academy;

public class Book {
    private Long id;
    private String title;
    private int price;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
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
