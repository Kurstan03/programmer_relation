package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.entity.Country;
import org.example.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:29
 */
public class CountryRepositoryImpl implements CountryRepository, AutoCloseable {
    private EntityManagerFactory entityManagerFactory = HibernateConfig.entityManagerFactory();
    @Override
    public void saveCountry(Country country) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(country);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveCollectionCountry(List<Country> countries) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Country country : countries) {
            entityManager.persist(country);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Country> getAllCountry() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Country> countries = entityManager.createQuery("from Country ", Country.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return countries;
    }

    @Override
    public Optional<Country> findCountryById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Country country = entityManager.find(Country.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return Optional.ofNullable(country);
    }

    @Override
    public void deleteCountryById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Country country = entityManager
                .createQuery("from Country c where c.id = :id", Country.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.remove(country);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteAllCountry() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Country> countries = entityManager.createQuery("from Country ", Country.class).getResultList();
        for (Country country : countries) {
            entityManager.remove(country);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateCountry(Long oldCountryId, Country newCountry) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("update Country set country = :country, description = :description where id = :id")
                        .setParameter("country", newCountry.getCountry())
                                .setParameter("description", newCountry.getDescription())
                                        .setParameter("id", oldCountryId)
                                                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Country findCountryWithTheLongestDescription() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Country country = entityManager
                .createQuery("from Country c where length(c.description) = " +
                        "(select max(length(c.description)) from Country c)", Country.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return country;
    }

    @Override
    public Long countCountry(String countryName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Long count = entityManager
                .createQuery("from Country c where c.country = :country", Country.class)
                .setParameter("country", countryName)
                .getResultStream().count();
        entityManager.getTransaction().commit();
        entityManager.close();
        return count;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
