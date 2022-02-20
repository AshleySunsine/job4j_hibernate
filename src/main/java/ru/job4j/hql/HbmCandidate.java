package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmCandidate {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

           /** Candidate one = new Candidate("Alex", "good", 200);
            Candidate two = new Candidate("Nikolay", "good", 300);
            Candidate three = new Candidate("Nikita", "good", 400);

            session.save(one);
            session.save(two);
            session.save(three);**/


           Query query = session.createQuery("from Candidate");
            for (Object q : query.list()) {
                System.out.println(q);
            }

             getCandidate(1, session);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            registry.close();
        }
    }

    public static Candidate getCandidate(int id, Session session) {
          Candidate cand = (Candidate) session.createQuery("from Candidate c where c.id = :cid").setParameter("cid", 1).uniqueResult();
         System.out.println(cand);
         return cand;
    }

    public static Candidate getCandidate(String name, Session session) {
        Candidate cand = (Candidate) session.createQuery("from Candidate c where c.name = :cname").setParameter("cname", "Nikita").uniqueResult();
        System.out.println(cand);
        return cand;
    }

    public static void setCandidate(int id, String newName, Session session) {
         session.createQuery("update Candidate c set c.name = :cName where c.id = :cId")
         .setParameter("cName", newName)
         .setParameter("cId", id).executeUpdate();
    }

    public static void deleteCandidate(int id, Session session) {
        session.createQuery("DELETE from Candidate c where c.id = id").executeUpdate();
    }

    public static void insertCandidate(String newName, Session session) {
          session.createQuery("insert into Candidate (name, experience, salary) "
         + "select concat(c.name, :newName), c.experience, c.salary "
         + "from Candidate c where c.id = 1").setParameter("newName", newName).executeUpdate();
    }

}
