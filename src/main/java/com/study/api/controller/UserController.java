package com.study.api.controller;

import com.study.api.entity.Customer;
import com.study.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final CustomerRepository repository;

    @GetMapping
    public List<Customer> findAll(){
        return repository.findAll();
    }
}
