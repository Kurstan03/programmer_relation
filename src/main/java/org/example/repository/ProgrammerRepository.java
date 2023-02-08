package org.example.repository;

import org.example.entity.Programmer;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:26
 */
public interface ProgrammerRepository {
    void save(Programmer programmer);
    void saveMore(List<Programmer> programmers);
    List<Programmer> getAllProgrammer();
    Optional<Programmer> finProgrammerById(Long id);
    void deleteProgrammerById(Long id);
    void deleteAllProgrammer();
    void update(Long oldProgrammerId, Programmer newProgrammer);
    List<Programmer> findProgrammerByCountry(String countryName);
    String findYoungestProgrammer();
    String findOldestProgrammer();


}
