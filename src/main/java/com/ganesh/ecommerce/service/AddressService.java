package com.ganesh.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganesh.ecommerce.model.Address;
import com.ganesh.ecommerce.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address saveAddress(
            Address address) {

        return addressRepository.save(
                address
        );
    }

    public List<Address> getAddresses(
            int userId) {

        return addressRepository.findByUserId(
                userId
        );
    }

    public void deleteAddress(
            int id) {

        addressRepository.deleteById(id);
    }
}