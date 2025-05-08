package mate.academy.model;

public class Book {
    private Long id;
    private String name;
    private String title;

    public Book() {
    }

    public Book(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public Book(Long id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", title='" + title + '\''
                + '}';
    }
}
