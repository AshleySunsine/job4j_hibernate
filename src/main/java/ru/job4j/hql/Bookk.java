package ru.job4j.hql;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Bookk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String publishingHouse;

    public static Bookk of(String name, String publishingHouse) {
        Bookk b = new Bookk();
        b.name = name;
        b.publishingHouse = publishingHouse;
        return b;
    }

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

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Override
    public String toString() {
        return "Book{"
              + "id=" + id
              + ", name='" + name + '\''
              + ", publishingHouse='" + publishingHouse + '\''
              + '}';
    }
}