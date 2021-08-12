package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.Address;
import uz.pdp.demo.entity.Company;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.CompanyDto;
import uz.pdp.demo.repository.AddressRepository;
import uz.pdp.demo.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return null;
        }
        return optionalCompany.get();
    }


    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists) {
            return new ApiResponse("Bunday nomli kompanya mavjud!", false);
        }
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Address address = new Address();
        address.setHomeNumber(companyDto.getHomeNumber());
        address.setStreet(companyDto.getStreet());
        addressRepository.save(address);
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Kompanya muvaffaqiyatli qushildii", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) {
            return new ApiResponse("Bunday ID lik kompnya mavjud emas", false);
        }

        boolean exists = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (exists) {
            return new ApiResponse("Bunday nomli kompnya mavjud", false);
        }
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        addressRepository.save(address);
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Kompanya muvaffaqiyatli tahrirlandi",true );
    }

    public ApiResponse deleteById(Integer id){
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("Bunday ID lik kompnya mavjud emas", false);
        }
        companyRepository.deleteById(id);
        return new ApiResponse("uchirildi",true);
    }
}
