package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Author;
import ru.job4j.hibernate.model.Book;

public class AuthorBookStore {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book oneBook = Book.of("Kazan");
            Book twoBook = Book.of("Piter");
            Book treeBook = Book.of("Okun");

            Author first = Author.of("Nikolay");
            Author second = Author.of("Sergey");
            Author thirst = Author.of("Anton");

            first.addBook(oneBook);
            first.addBook(twoBook);
            first.addBook(treeBook);

            second.addBook(treeBook);
            second.addBook(twoBook);
            thirst.addBook(twoBook);

            session.persist(first);
            session.persist(second);
            session.persist(thirst);

            session.getTransaction().commit();
            session.close();

            Session session2 = sf.openSession();
            session2.beginTransaction();
            Author author = session2.get(Author.class, 5);
            session2.remove(author);
            session2.getTransaction().commit();
            session2.close();

        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}