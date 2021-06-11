package com.carbon.hospitalitysystem.dao.impl;

import com.carbon.hospitalitysystem.dao.CustomerDao;
import com.carbon.hospitalitysystem.entities.CustomerEntity;
import com.carbon.hospitalitysystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDaoImpl extends CrudDaoImpl<CustomerEntity,Long> implements CustomerDao {
    private CustomerRepository repository;
    public CustomerDaoImpl(CustomerRepository repository) {
        super(repository);
        this.repository =repository;
    }

    @Override
    public List<CustomerEntity> saveAll(Iterable<CustomerEntity> customers) {
        return repository.saveAll(customers);
    }
}
