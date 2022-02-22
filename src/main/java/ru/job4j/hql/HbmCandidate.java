package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Candidateee;
import ru.job4j.model.VacansionBase;

import java.util.ArrayList;
import java.util.List;

public class HbmCandidate {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

         /**   Candidateee one = new Candidateee("Alex", "good", 200);
            Candidateee two = new Candidateee("Nikolay", "good", 300);
            Candidateee three = new Candidateee("Nikita", "good", 400);

            session.save(one);
            session.save(two);
            session.save(three);**/

           Candidateee can = session.createQuery("select c from Candidateee c join fetch c.vac where c.id=:cId",
                           Candidateee.class)
                   .setParameter("cId", 1).uniqueResult();
            VacansionBase vb = session.createQuery("select vb from VacansionBase vb join fetch vb.vacs where vb.id= :vbId",
                            VacansionBase.class)
                            .setParameter("vbId", 1).uniqueResult();


            System.out.println(can);
            System.out.println(vb);

          /** Query query = session.createQuery("from Candidateee");
            for (Object q : query.list()) {
                System.out.println(q);
            }

             getCandidate(1, session);**/

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            registry.close();
        }
    }

    public static Candidateee getCandidate(int id, Session session) {
          Candidateee cand = (Candidateee) session.createQuery("from Candidateee c where c.id = :cid")
                  .setParameter("cid", 1)
                  .uniqueResult();
         System.out.println(cand);
         return cand;
    }

    public static List<Candidateee> getCandidate(String name, Session session) {
        return session.createQuery("from Candidateee c where c.name = :cname")
                .setParameter("cname", "Nikita")
                .list();
    }

    public static void setCandidate(int id, String newName, Session session) {
         session.createQuery("update Candidateee c set c.name = :cName where c.id = :cId")
         .setParameter("cName", newName)
         .setParameter("cId", id).executeUpdate();
    }

    public static void deleteCandidate(int id, Session session) {
        session.createQuery("DELETE from Candidateee c where c.id = id").executeUpdate();
    }

    public static void insertCandidate(String newName, Session session) {
          session.createQuery("insert into Candidateee (name, experience, salary) "
         + "select concat(c.name, :newName), c.experience, c.salary "
         + "from Candidateee c where c.id = 1").setParameter("newName", newName).executeUpdate();
    }

}
