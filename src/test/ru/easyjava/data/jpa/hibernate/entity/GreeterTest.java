package ru.easyjava.data.jpa.hibernate.entity;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GreeterTest {
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        Greeter greetJpa = new Greeter();
        greetJpa.setGreeting("Hello");
        greetJpa.setTarget("JPA");

        Greeter greetHibernate = new Greeter();
        greetHibernate.setGreeting("Hello");
        greetHibernate.setTarget("Hibernate");

        entityManagerFactory = Persistence.createEntityManagerFactory("ru.easyjava.data.jpa.hibernate");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(greetJpa);
        em.persist(greetHibernate);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testGreeter() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("from Greeter", Greeter.class)
                .getResultList()
                .forEach(g -> System.out.println(String.format("%s, %s!", g.getGreeting(), g.getTarget())));
        em.getTransaction().commit();
        em.close();
    }
}