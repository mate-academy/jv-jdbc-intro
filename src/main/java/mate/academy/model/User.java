package mate.academy.model;

public class User {
    private int id;
    private final String name;
    private final String email;
    private final String password;
    private final String fileData;

    public User(String name, String email, String password, String fileData) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.fileData = fileData;
    }


    public User(int id, String name, String email, String password, String fileData) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.fileData = fileData;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFileData() {
        return fileData;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fileData(length)=" + (fileData != null ? fileData.length() : 0) +
                '}';
    }
}
