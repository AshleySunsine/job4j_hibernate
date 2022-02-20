package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookk> bookks = new ArrayList<>();

    public void addBook(Bookk bookk) {
        this.bookks.add(bookk);
    }

    public static Account of(String username) {
        Account a = new Account();
        a.username = username;
        a.active = true;
        return a;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Bookk> getBooks() {
        return bookks;
    }

    public void setBooks(List<Bookk> bookks) {
        this.bookks = bookks;
    }

    @Override
    public String toString() {
        return "Account{"
               + "id=" + id
               + ", username='" + username + '\''
               + ", active=" + active
               + ", books=" + bookks
               + '}';
    }
}
