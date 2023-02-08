package org.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Address;
import org.example.entity.Country;
import org.example.entity.Programmer;
import org.example.entity.Project;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

/**
 * @author kurstan
 * @created at 08.02.2023 2:29
 */
public class HibernateConfig {
    public static EntityManagerFactory entityManagerFactory(){
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
        properties.setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.setProperty(Environment.USER, "postgres");
        properties.setProperty(Environment.PASS, "postgres");
        properties.setProperty(Environment.HBM2DDL_AUTO, "create");
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(Environment.SHOW_SQL, "true");
        properties.setProperty(Environment.FORMAT_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(Programmer.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Country.class);
        configuration.addAnnotatedClass(Project.class);

        return configuration.buildSessionFactory().unwrap(EntityManagerFactory.class);
    }
}
