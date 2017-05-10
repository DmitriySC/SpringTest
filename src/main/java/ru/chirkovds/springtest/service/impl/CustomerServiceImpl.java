package ru.chirkovds.springtest.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chirkovds.springtest.entity.Customer;
import ru.chirkovds.springtest.repositiry.CustomerRepository;
import ru.chirkovds.springtest.service.CustomerService;

import java.util.List;

@Service("jpaCustomerService")
@Repository
@Transactional

public class CustomerServiceImpl implements CustomerService{
    private Log log = LogFactory.getLog(CustomerService.class);
    private CustomerRepository customerRepository;

    @Transactional (readOnly = true)
    public List<Customer> findAll() {
        return Lists.newArrayList(customerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Customer findOne(Long id) {
        log.info("Finding Customer ID: " + id);
        Customer customer = customerRepository.findOne(id);
            try {
                Hibernate.initialize(customer.getTroubleList().size());
            } catch (NullPointerException e) {
                log.info("TroubleList is Empty");
            }
        return customer;
    }

    public Customer saveClient(Customer customer) {
        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        if (customer.getId() == null) {
            log.info("Customer ID: " + customer.getId() + " inserting.");
        } else {
            log.info("Customer ID: " + customer.getId() + " updating.");
        }
        return savedCustomer;
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(id);
        log.info("Customer ID: " + id + " was deleted.");
    }

    public List<Customer> findFirst10ByLastNameContaining (String like){
        log.info("Finding Customer like: " + like);
        return customerRepository.findFirst10ByLastNameContaining(like);
    }
    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
}