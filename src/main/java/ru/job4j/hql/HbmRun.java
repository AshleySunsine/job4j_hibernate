package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Student rsl = null;

        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

/**
           Student one = Student.of("Alex", 21, "Moscow");
           Student two = Student.of("Nikolay", 28, "Saint-Petersburg");
           Student three = Student.of("Nikita", 25, "Kaliningrad");

           session.save(one);
           session.save(two);
           session.save(three);
**/

          /**  rsl = session.createQuery("select distinct st from Student st "
                           + "join fetch st.account a "
                           + "join fetch a.bookks b "
                           + "where st.id = :sId", Student.class)
                            .setParameter("sId", 7)
                                    .uniqueResult();**/


            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        System.out.println(rsl);
    }
}
