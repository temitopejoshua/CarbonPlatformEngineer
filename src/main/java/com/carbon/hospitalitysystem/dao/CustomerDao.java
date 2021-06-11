package com.carbon.hospitalitysystem.dao;

import com.carbon.hospitalitysystem.entities.CustomerEntity;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity,Long> {
    List<CustomerEntity> saveAll(Iterable<CustomerEntity> customers);

}
