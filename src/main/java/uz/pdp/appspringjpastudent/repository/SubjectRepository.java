package uz.pdp.appspringjpastudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringjpastudent.entity.Student;
import uz.pdp.appspringjpastudent.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    boolean existsByName(String name);

    Optional<Student> findById(List<Integer> subjectId);
}
