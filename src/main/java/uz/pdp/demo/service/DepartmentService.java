package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.demo.entity.Company;
import uz.pdp.demo.entity.Department;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.DepartmentDto;
import uz.pdp.demo.repository.CompanyRepository;
import uz.pdp.demo.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> byId = departmentRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        return departmentRepository.getById(id);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByNameAndAndCompany_Id(
                departmentDto.getName(), departmentDto.getCompanyId());
        if (exists) {
            return new ApiResponse("Kompanyada bunday nomli department mmavjud", false);
        }
        Optional<Company> optional = companyRepository.findById(departmentDto.getCompanyId());
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday Idlik kompanya mavjud emas", false);
        }
        Company company = optional.get();
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department Muvaffaqiyatli qushildi", true);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday Idlik department mavjud emas", false);
        }
        boolean exists = departmentRepository.existsByNameAndCompany_IdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (exists) {
            return new ApiResponse("Bu kompanyada bunday department mavjud!", false);
        }
        Optional<Company> optional1 = companyRepository.findById(departmentDto.getCompanyId());
        if (optional1.isEmpty()) {
            return new ApiResponse("Bunday Idlik kompanya mavjud emsas", false);
        }
        Department department = optional.get();
        department.setName(departmentDto.getName());
        Company company = optional1.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department tahrirlandi", true);
    }

    public ApiResponse deleteDepartmentById(Integer id) {
        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday Idlik department mavjud emas", false);
        }
        departmentRepository.deleteById(id);
        return new ApiResponse("Department uchirildi", true);
    }
}
