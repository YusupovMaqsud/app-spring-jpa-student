package uz.pdp.appspringjpastudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringjpastudent.entity.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
}
