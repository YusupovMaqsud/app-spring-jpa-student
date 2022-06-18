package uz.pdp.appspringjpastudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringjpastudent.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University,Integer> {
}
