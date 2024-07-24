package app;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String nic;
    private String password;

    // Constructors
    public User(int id, String name, String nic, String password) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.password = password;
    }

    public User(String name, String nic, String password) {
        this.name = name;
        this.nic = nic;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNic() {
        return nic;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString method
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', nic='" + nic + "', password='" + password + "'}";
    }

    // Override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(nic, user.nic) &&
                Objects.equals(password, user.password);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, name, nic, password);
    }
}
