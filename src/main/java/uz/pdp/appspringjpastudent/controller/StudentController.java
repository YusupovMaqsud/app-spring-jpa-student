package uz.pdp.appspringjpastudent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringjpastudent.entity.Address;
import uz.pdp.appspringjpastudent.entity.Group;
import uz.pdp.appspringjpastudent.entity.Student;
import uz.pdp.appspringjpastudent.entity.Subject;
import uz.pdp.appspringjpastudent.payload.StudentDto;
import uz.pdp.appspringjpastudent.repository.AddressRepository;
import uz.pdp.appspringjpastudent.repository.GroupRepository;
import uz.pdp.appspringjpastudent.repository.StudentRepository;
import uz.pdp.appspringjpastudent.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;


    //Vazirlik uchun
    @GetMapping("/forMinistry")
    public Page<Student> getStudentForMinistry(@RequestParam int page) {
        //select*from student limit 10 offset (0*10) degani
        //select*from student limit 10 offset (1*10) degani
        //select*from student limit 10 offset (2*10) degani
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //University
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentForUniversity(@PathVariable Integer universityId,
                                                 @RequestParam int page) {
        //select*from student limit 10 offset (0*10) degani
        //select*from student limit 10 offset (1*10) degani
        //select*from student limit 10 offset (2*10) degani
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findByGroup_Faculty_University_id(universityId, pageable);
        return studentPage;
    }


    //Faculty dekanat
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentForFaculty(@PathVariable Integer facultyId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Student> studentPage = studentRepository.findByGroup_Faculty_Id(facultyId, pageable);
        return studentPage;
    }


    //Group owner
    @GetMapping("/forGroup/{groupId}")
    public Page<Student> getStudentForGroup(@PathVariable Integer groupId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Student> studentPage = studentRepository.findByGroup_Id(groupId, pageable);
        return studentPage;
    }


    @PostMapping("addStudent")
    public String addStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        boolean exist = studentRepository.existsStudentByFirstNameAndLastNameAndGroup_Id(studentDto.getFirstName()
                , studentDto.getLastName()
                , studentDto.getGroupId());

        if (exist) {
            return "This student already exist";
        }
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = new Address();
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        Address saveAddress = addressRepository.save(address);
        student.setAddress(saveAddress);

        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent()) {
            return "Group not found";
        }
        Group group = optionalGroup.get();
        student.setGroup(group);

        Optional<Student> byId = subjectRepository.findById(studentDto.getSubjectId());
        if(!byId.isPresent()){
            return "Subject not found";
        }
        Student student1 = byId.get();
        student.setSubject((List<Subject>) student1);

        studentRepository.save(student);
        return "Student added";

    }


    @PutMapping("/editstudent/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            boolean exist = studentRepository.existsStudentByFirstNameAndLastNameAndGroup_Id(studentDto.getFirstName()
                    , studentDto.getLastName()
                    , studentDto.getGroupId());

            if (exist) {
                return "This student already exist";
            }

            Student student = optionalStudent.get();
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());

            Address address = new Address();
            address.setCity(studentDto.getCity());
            address.setDistrict(studentDto.getDistrict());
            address.setStreet(studentDto.getStreet());
            addressRepository.save(address);

            Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
            if (!optionalGroup.isPresent()) {
                return "Group not found";
            }
            Group group = optionalGroup.get();
            student.setGroup(group);

            Optional<Student> byId = subjectRepository.findById(studentDto.getSubjectId());
            if(!byId.isPresent()){
                return "Subject not found";
            }
                Student student1 = byId.get();
                student.setSubject((List<Subject>) student1);

                studentRepository.save(student);
                return "Student edited";

        }
        return "null";
    }



    @DeleteMapping("/deletedStudent/{id]")
    public String deletedStudent(@PathVariable Integer id){
        studentRepository.deleteById(id);
        return "Student deleted";
    }
}
