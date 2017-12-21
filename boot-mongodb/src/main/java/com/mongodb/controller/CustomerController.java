package com.mongodb.controller;

import com.mongodb.dao.CustomerRepository;
import com.mongodb.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") String id) {
        return customerRepository.findOne(id + "");
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String postCustomer(@RequestParam(value = "id") String id,
                              @RequestParam(value = "firstName") String firstName,
                               @RequestParam(value = "lastName") String lastName) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        Customer customer1 = customerRepository.insert(customer);
        return customer1.toString();
    }
}
