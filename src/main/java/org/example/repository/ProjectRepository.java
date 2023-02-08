package org.example.repository;

import org.example.entity.Programmer;
import org.example.entity.Project;

import java.util.List;
import java.util.Optional;

/**
 * @author kurstan
 * @created at 08.02.2023 3:28
 */
public interface ProjectRepository {
    void save(Project project);
    void saveMore(List<Project> projects);
    List<Project> getAllProject();
    Optional<Project> findProjectById(Long id);
    void deleteProjectById(Long id);
    void deleteAllProject();
    void update(Long oldProjectId, Project newProject);
    void assignProgrammerToProject(Long projectId, Programmer programmer);
    Project getTheMostExpensiveProject();
    Project getProjectThatIsWrittenInTheShortestPossibleTime();
}
