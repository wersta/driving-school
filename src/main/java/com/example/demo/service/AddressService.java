package com.example.demo.service;

import com.example.demo.repository.AddressRepository;
import com.example.demo.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public void saveAddress(Address address){
        addressRepository.save(address);
    }
}
