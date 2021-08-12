package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
