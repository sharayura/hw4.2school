package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findFacultyByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
