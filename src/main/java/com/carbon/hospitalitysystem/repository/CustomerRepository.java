package com.carbon.hospitalitysystem.repository;

import com.carbon.hospitalitysystem.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
}
