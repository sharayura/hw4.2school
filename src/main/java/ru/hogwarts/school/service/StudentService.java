package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("Method createStudent was called");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.debug("Method findStudent was called");
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> findStudentByAgeBetween(int min, int max) {
        logger.debug("Method findStudentByAgeBetween was called");
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    public Student editStudent(Student student) {
        logger.debug("Method editStudent was called");
        if (findStudent(student.getId()) == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.debug("Method deleteStudent was called");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.debug("Method getStudentsByAge was called");
        return studentRepository.findStudentByAge(age);
    }

    public Long getFacultyIdOfStudent(Long id) {
        logger.debug("Method getFacultyIdOfStudent was called");
        if (findStudent(id) == null) {
            return null;
        }
        return findStudent(id).getFacultyId();
    }

    public int getNumberOfAllStudents() {
        logger.debug("Method getNumberOfAllStudents was called");
        return studentRepository.getNumberOfAllStudents();
    }

    public int getAverageAge() {
        logger.debug("Method getAverageAge was called");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> get5MaxId() {
        logger.debug("Method get5MaxId was called");
        return studentRepository.get5MaxId();
    }

    public List<String> findNamesBeginA() {
        return studentRepository.findAll().stream()
                .map(s -> s.getName().toUpperCase())
                .filter(s -> s.charAt(0) == 'A')
                .sorted().toList();
    }

    public Double averageAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average().orElseThrow();
    }

    public void studentNamesConsole() {

        System.out.println(studentName(0));
        System.out.println(studentName(1));

        new Thread(() -> {
            System.out.println(studentName(2));
            System.out.println(studentName(3));
        }).start();

        new Thread(() -> {
            System.out.println(studentName(4));
            System.out.println(studentName(5));
        }).start();
    }

    public String studentName(int index) {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .toList().get(index);
    }

    public void studentNamesConsoleSynchro() {
        printTwoNamesSynchro(0, 1);

        new Thread(() -> {
            printTwoNamesSynchro(2, 3);
        }).start();

        new Thread(() -> {
            printTwoNamesSynchro(4, 5);
        }).start();
    }

    public void printTwoNamesSynchro(int index1, int index2) {
        synchronized (studentRepository) {
            System.out.println(studentName(index1));
            System.out.println(studentName(index2));
        }
    }

}
