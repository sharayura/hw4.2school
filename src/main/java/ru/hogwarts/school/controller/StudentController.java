package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        student = studentService.editStudent(student);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int age) {
        Collection<Student> foundStudents = studentService.getStudentsByAge(age);
        if (foundStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudents);
    }

    @GetMapping("age")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int min,
                                                                       @RequestParam int max) {
        return ResponseEntity.ok(studentService.findStudentByAgeBetween(min, max));
    }

    @GetMapping("faculty")
    public ResponseEntity<Long> getFacultyOfStudent(@RequestParam Long id) {
        Long foundFaculty = studentService.getFacultyIdOfStudent(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }


    @GetMapping("number")
    public ResponseEntity<Integer> getNumberOfAllStudents() {
        return ResponseEntity.ok(studentService.getNumberOfAllStudents());
    }

    @GetMapping("avg-age")
    public ResponseEntity<Integer> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("5last")
    public ResponseEntity<Collection<Student>> get5MaxId() {
        return ResponseEntity.ok(studentService.get5MaxId());
    }

    @GetMapping("names/a")
    public ResponseEntity<List<String>> findNamesBeginA() {
        return ResponseEntity.ok(studentService.findNamesBeginA());
    }

    @GetMapping("age/average")
    public ResponseEntity<Double> averageAge() {
        return ResponseEntity.ok(studentService.averageAge());
    }

    @GetMapping("names-console")
    public void studentNamesConsole() {
        studentService.studentNamesConsole();
    }

    @GetMapping("names-console-synchro")
    public void studentNamesConsoleSynchro() {
        studentService.studentNamesConsoleSynchro();
    }
}
