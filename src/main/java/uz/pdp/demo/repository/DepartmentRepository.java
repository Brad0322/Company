package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndAndCompany_Id(String name, Integer company_id);
    boolean existsByNameAndCompany_IdAndIdNot(String name, Integer company_id, Integer id);
}
