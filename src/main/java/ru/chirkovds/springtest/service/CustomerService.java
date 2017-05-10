package ru.chirkovds.springtest.service;

import ru.chirkovds.springtest.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();
    Customer findOne(Long id);
    Customer saveClient(Customer customer);
    void deleteCustomer(Long id);
    List<Customer> findFirst10ByLastNameContaining (String like);
}
