package com.frankmoley.lil.adminweb.data.repository;

import com.frankmoley.lil.adminweb.data.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
