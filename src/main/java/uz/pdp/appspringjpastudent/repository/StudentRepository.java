package uz.pdp.appspringjpastudent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringjpastudent.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Page<Student> findByGroup_Faculty_University_id(Integer group_faculty_university_id, Pageable pageable);
    Page<Student> findByGroup_Faculty_Id(Integer group_faculty_id, Pageable pageable);
    Page<Student> findByGroup_Id(Integer group_id, Pageable pageable);
    boolean existsStudentByFirstNameAndLastNameAndGroup_Id(String firstName, String lastName, Integer group_id);
}
