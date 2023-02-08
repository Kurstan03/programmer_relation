package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.config.HibernateConfig;
import org.example.entity.Programmer;
import org.example.entity.Project;
import org.example.repository.ProgrammerRepository;
import org.example.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:30
 */
public class ProjectRepositoryImpl implements ProjectRepository, AutoCloseable {
    private EntityManagerFactory entityManagerFactory = HibernateConfig.entityManagerFactory();


    @Override
    public void save(Project project) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void saveMore(List<Project> projects) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        for (Project project : projects) {
            entityManager.persist(project);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Project> getAllProject() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Project> projects = entityManager
                .createQuery("from Project ", Project.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return projects;
    }

    @Override
    public Optional<Project> findProjectById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Project project = entityManager
                    .createQuery("from Project where id = :id", Project.class)
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return Optional.of(project);
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteProjectById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Project project = entityManager
                .createQuery("from Project where id = :id ", Project.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.remove(project);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteAllProject() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Project ").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void update(Long oldProjectId, Project newProject) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("update Project set name = :n, description = :d, dateOfStart = :ds," +
                        "dateOfFinish = :df, price = :p where id = :id").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void assignProgrammerToProject(Long projectId, Programmer programmer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Project project = entityManager
                .createQuery("from Project where id = :id", Project.class)
                .setParameter("id", projectId)
                .getSingleResult();
        project.getProgrammers().add(programmer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Project getTheMostExpensiveProject() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Project project = entityManager
                .createQuery("from Project where price = (select max(price) from Project )", Project.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return project;
    }

    @Override
    public Project getProjectThatIsWrittenInTheShortestPossibleTime() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Project project = entityManager
                .createQuery("from Project where dateOfFinish - dateOfStart =" +
                        " (select min(dateOfFinish - dateOfStart) from Project )", Project.class)
                .getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
