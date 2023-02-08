package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        faculty = facultyService.editFaculty(faculty);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("color/{color}")
    public ResponseEntity<Faculty> getFacultiesByColor(@PathVariable String color) {
        Faculty foundFaculties = facultyService.getFacultiesByColor(color);
        if (foundFaculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculties);
    }

    @GetMapping
    public ResponseEntity<Faculty> findFacultyByNameOrColor(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String color) {
        Faculty foundFaculty = facultyService.findFacultyByNameOrColor(name, color);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("students")
    public ResponseEntity<Collection<Student>> getStudentsFromFaculty(@RequestParam Long id) {
        if (facultyService.findFaculty(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.getStudentsFromFaculty(id));
    }

}
