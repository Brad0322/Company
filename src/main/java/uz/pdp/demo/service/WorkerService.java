package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.Address;
import uz.pdp.demo.entity.Department;
import uz.pdp.demo.entity.Worker;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.WorkerDto;
import uz.pdp.demo.repository.AddressRepository;
import uz.pdp.demo.repository.DepartmentRepository;
import uz.pdp.demo.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, DepartmentRepository departmentRepository, AddressRepository addressRepository) {
        this.workerRepository = workerRepository;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
    }

    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optional = workerRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public ApiResponse addWorker(WorkerDto workerDto) {
        Optional<Department> optional = departmentRepository.findById(workerDto.getDepartmentId());
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday Idlik department mavjud emas", false);
        }
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        try {
            worker.setPhoneNumber(workerDto.getPhoneNumber());
        } catch (Exception e) {
            return new ApiResponse("Bunday raqamli xodim mavjud!", false);
        }
        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        addressRepository.save(address);
        worker.setAddress(address);
        worker.setDepartment(optional.get());
        workerRepository.save(worker);
        return new
                ApiResponse("Xodim muvaffaqiyatli qushildi", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optional = workerRepository.findById(id);
        Optional<Department> optional1 = departmentRepository.findById(workerDto.getDepartmentId());
        if (optional.isEmpty() || optional1.isEmpty()) {
            return new ApiResponse("Bunday Idlik xodim mavjud emas yoki department topilmadi", false);
        }
        Worker worker = optional.get();
        Department department = optional1.get();
        worker.setName(workerDto.getName());
        try {
            worker.setPhoneNumber(workerDto.getPhoneNumber());
        } catch (Exception e) {
            return new ApiResponse("Bunday raqamli xodim mavjud!", false);
        }
        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        addressRepository.save(address);
        worker.setAddress(address);
        worker.setDepartment(optional1.get());
        workerRepository.save(worker);
        return new ApiResponse("Xodim malumotlari muvafaqiyatli tahrirlandi", true);
    }

    public ApiResponse deleteById(Integer id) {
        Optional<Worker> optional = workerRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday ID lik xodim topilmadi", false);
        }
        workerRepository.deleteById(id);
        return new ApiResponse("Xodim uchirildi", true);
    }

}
