package pl.polsl.staneczek.service;

import pl.polsl.staneczek.repository.AddressRepository;
import pl.polsl.staneczek.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void saveAddress(Address address){
        addressRepository.save(address);
    }
    public void deleteAddress(Address address){
        addressRepository.delete(address);
    }
    public Address findById(Integer studentId) {
        return addressRepository.findById(studentId).isPresent() ? addressRepository.findById(studentId).get() : null;
    }
}

