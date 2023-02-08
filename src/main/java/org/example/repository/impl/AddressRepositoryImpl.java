package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.config.HibernateConfig;
import org.example.entity.Address;
import org.example.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:29
 */
public class AddressRepositoryImpl implements AddressRepository, AutoCloseable {
    private EntityManagerFactory entityManagerFactory = HibernateConfig.entityManagerFactory();
    @Override
    public void saveAddress(Address address) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveMoreAddress(List<Address> addresses) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Address address : addresses) {
            entityManager.persist(address);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Address> getAllAddresses() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Address> addresses = entityManager.createQuery("from Address ", Address.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return addresses;
    }

    @Override
    public Optional<Address> findAddressById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Address address = entityManager.createQuery("from Address where id = : id", Address.class)
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.of(address);
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteAddressById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Address address = entityManager
                .createQuery("from Address a where a.id = :id", Address.class)
                .getSingleResult();
        entityManager.remove(address);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteAllAddress() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Address ").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateAddress(Long oldAddressId, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("update Address set regionName = :rn, " +
                        "street = :s, homeNumber = :hm where id = :id", Address.class)
                        .setParameter("rn", newAddress.getRegionName())
                                .setParameter("s", newAddress.getStreet())
                                        .setParameter("hm", newAddress.getHomeNumber())
                                                .setParameter("id", oldAddressId)
                                                        .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
