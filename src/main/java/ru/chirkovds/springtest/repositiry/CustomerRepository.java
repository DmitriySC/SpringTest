package ru.chirkovds.springtest.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chirkovds.springtest.entity.Customer;
import java.util.List;

public interface CustomerRepository extends JpaRepository <Customer, Long> {
    List<Customer> findByLastNameContaining(String like);
}
