package ru.job4j.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Car;
import ru.job4j.model.Mark;

import java.util.ArrayList;
import java.util.List;

public class Store {

    public static void main(String[] args) {
        List<Mark> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Mark wolt = Mark.of("Wolt");
            Mark audi = Mark.of("Audi");

            Car one = Car.of("First", wolt);
            Car two = Car.of("Second", wolt);
            Car three = Car.of("Three", wolt);
            Car four = Car.of("Four", audi);
            Car five = Car.of("Five", audi);
            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            wolt.addCar(session.load(Car.class, 1));
            wolt.addCar(session.load(Car.class, 2));
            wolt.addCar(session.load(Car.class, 3));
            audi.addCar(session.load(Car.class, 4));
            audi.addCar(session.load(Car.class, 5));

            session.save(wolt);
            session.save(audi);

            session.getTransaction().commit();
            session.close();

            Session session2 = sf.openSession();
            session2.beginTransaction();
            list = session2.createQuery("select distinct c from Mark c join fetch c.cars").list();
            session2.getTransaction().commit();
            session2.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Mark mark : list) {
            for (Car car : mark.getCars()) {
                System.out.println(car);
            }
        }
    }
}