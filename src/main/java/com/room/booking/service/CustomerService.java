package com.room.booking.service;

import com.room.booking.entity.Customer;
import com.room.booking.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    Optional<Customer> getCustomerByEmail(String customerEmail) {
        return customerRepository.findByEmailAddress(customerEmail);
    }

    Customer getCustomer(Customer customer) {
        final Optional<Customer> customerByEmail = getCustomerByEmail(customer.getEmailAddress());
        return customerByEmail.orElseGet(() -> customerRepository.save(customer));
    }
}
