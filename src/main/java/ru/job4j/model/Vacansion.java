package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "vacansions")
public class Vacansion {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacansion vacansion = (Vacansion) o;
        return id == vacansion.id && Objects.equals(name, vacansion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Vacansion{"
              + "id=" + id
              + ", name='" + name + '\''
              + '}';
    }
}
