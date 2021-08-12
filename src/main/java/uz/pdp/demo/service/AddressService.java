package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.demo.entity.Address;
import uz.pdp.demo.payload.AddressDto;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.repository.AddressRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Integer id) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public ApiResponse addAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("qushildi", true);
    }

    public ApiResponse editAddress(Integer id,AddressDto addressDto){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new ApiResponse("bunday ID lik address mavjud emas",false);
        }
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address tahrirlandi",true);
    }

    public ApiResponse deleteAddress(Integer id){
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("bunday ID lik address mavjud emsa",false);
        }
        addressRepository.deleteById(id);
        return new ApiResponse("Address uchirildi",true);
    }


}
