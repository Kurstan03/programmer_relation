package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.config.HibernateConfig;
import org.example.entity.Country;
import org.example.entity.Programmer;
import org.example.repository.ProgrammerRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:30
 */
public class ProgrammerRepositoryImpl implements ProgrammerRepository, AutoCloseable {
    private EntityManagerFactory entityManagerFactory = HibernateConfig.entityManagerFactory();

    @Override
    public void save(Programmer programmer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(programmer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveMore(List<Programmer> programmers) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Programmer programmer : programmers) {
            entityManager.persist(programmer);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Programmer> getAllProgrammer() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Programmer> programmers = entityManager
                .createQuery("from Programmer ", Programmer.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return programmers;
    }

    @Override
    public Optional<Programmer> finProgrammerById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Programmer programmer = entityManager
                    .createQuery("from Programmer where id = :id", Programmer.class)
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.of(programmer);
        } catch (NoResultException e){
            entityManager.getTransaction().commit();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteProgrammerById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Programmer programmer = entityManager
                .createQuery("from Programmer where id = :id", Programmer.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.remove(programmer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteAllProgrammer() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Programmer ").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void update(Long oldProgrammerId, Programmer newProgrammer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("update Programmer set fullName = :fn, email = :e, " +
                        "dateOfBirth = :d, status = :s where id = :id")
                        .setParameter("fn", newProgrammer.getFullName())
                                .setParameter("e", newProgrammer.getEmail())
                                        .setParameter("d", newProgrammer.getDateOfBirth())
                                                .setParameter("s", newProgrammer.getStatus())
                                                        .setParameter("id", oldProgrammerId)
                                                                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Programmer> findProgrammerByCountry(String countryName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Programmer> programmers = entityManager.createQuery("select p from Programmer p " +
                                "join p.address as a " +
                                "join a.country as c where c.country = : c",
                        Programmer.class)
                .setParameter("c", countryName)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return programmers;
    }

    @Override
    public String findYoungestProgrammer() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Programmer programmer = entityManager
                .createQuery("from Programmer p where dateOfBirth =" +
                        " (select min(p.dateOfBirth) from Programmer )", Programmer.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return programmer.getFullName() + "  age - " + LocalDate.now()
                .minusYears(programmer.getDateOfBirth().getYear()).getYear();
    }

    @Override
    public String findOldestProgrammer() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Programmer programmer = entityManager.createQuery("from Programmer where dateOfBirth = " +
                        "(select max(p.dateOfBirth) from Programmer p)", Programmer.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return programmer.getFullName() + "  age - " + LocalDate.now()
                .minusYears(programmer.getDateOfBirth().getYear()).getYear();
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
