package ru.job4j.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table  (name = "VacansionBases")
public class VacansionBase {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    String name;

    @OneToMany
    List<Vacansion> vacs = new ArrayList<>();

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

    public List<Vacansion> getVacs() {
        return vacs;
    }

    public void setVacs(List<Vacansion> vacs) {
        this.vacs = vacs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacansionBase that = (VacansionBase) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(vacs, that.vacs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vacs);
    }

    @Override
    public String toString() {
        return "VacansionBase{"
               + "id=" + id
               + ", name='" + name + '\''
               + ", vacs=" + vacs
               + '}';
    }
}
