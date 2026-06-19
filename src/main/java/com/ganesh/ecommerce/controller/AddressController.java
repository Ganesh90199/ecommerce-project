package com.ganesh.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ganesh.ecommerce.config.JwtUtil;
import com.ganesh.ecommerce.model.Address;
import com.ganesh.ecommerce.model.User;
import com.ganesh.ecommerce.repository.UserRepository;
import com.ganesh.ecommerce.service.AddressService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Address saveAddress(
            @RequestBody Address address,
            HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                       .substring(7);

        String email =
                jwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email)
                              .get();

        address.setUserId(
                user.getId()
        );

        return addressService.saveAddress(
                address
        );
    }

    @GetMapping
    public List<Address> getAddresses(
            HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                       .substring(7);

        String email =
                jwtUtil.extractEmail(token);

        User user =
                userRepository.findByEmail(email)
                              .get();

        return addressService.getAddresses(
                user.getId()
        );
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(
            @PathVariable int id) {

        addressService.deleteAddress(id);

        return "Address Deleted";
    }
}