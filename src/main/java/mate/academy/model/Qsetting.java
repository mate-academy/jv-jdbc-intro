package mate.academy.model;

public class Qsetting {
    private int id;
    private String name;
    private String ref;
    private String tune; // Stores XML as a String

    public Qsetting(String name, String ref, String tune) {
        this.name = name;
        this.ref = ref;
        this.tune = tune;
    }

    public Qsetting(int id, String name, String ref, String tune) {
        this.id = id;
        this.name = name;
        this.ref = ref;
        this.tune = tune;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getRef() { return ref; }
    public String getTune() { return tune; }

    @Override
    public String toString() {
        return "Qsetting{id=" + id + ", name='" + name + "', ref='" + ref + "', tune='" + tune + "'}";
    }
}
