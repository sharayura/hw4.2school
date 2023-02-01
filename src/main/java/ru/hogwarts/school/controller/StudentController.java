package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;
    private final AvatarRepository avatarRepository;

    public StudentController(StudentService studentService, AvatarService avatarService,
                             AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
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

    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/avatar/data")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
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
}
