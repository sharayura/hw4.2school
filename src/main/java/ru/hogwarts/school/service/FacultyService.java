package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Method createFaculty was called");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.debug("Method findFaculty was called");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Method editFaculty was called");
        if (findFaculty(faculty.getId()) == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.debug("Method deleteFaculty was called");
        facultyRepository.deleteById(id);
    }

    public Faculty getFacultiesByColor(String color) {
        logger.debug("Method getFacultiesByColor was called");
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Faculty findFacultyByNameOrColor(String name, String color) {
        logger.debug("Method findFacultyByNameOrColor was called");
        if (!(name == null || name.isBlank())) {
            color = null;
        }
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentsFromFaculty(Long id) {
        logger.debug("Method getStudentsFromFaculty was called");
        return findFaculty(id).getStudents();
    }

    public String longestNames() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}
