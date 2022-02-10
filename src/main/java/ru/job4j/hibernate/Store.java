package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Car;
import ru.job4j.hibernate.model.Mark;

public class Store {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car one = Car.of("First");
            Car two = Car.of("Second");
            Car three = Car.of("Three");
            Car four = Car.of("Four");
            Car five = Car.of("Five");
            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            Mark wolt = Mark.of("Wolt");
            wolt.addCar(session.load(Car.class, 1));
            wolt.addCar(session.load(Car.class, 2));
            wolt.addCar(session.load(Car.class, 3));
            wolt.addCar(session.load(Car.class, 4));
            wolt.addCar(session.load(Car.class, 5));

            session.save(wolt);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}