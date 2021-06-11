package com.carbon.hospitalitysystem.dao;

import java.util.Optional;

public interface CrudDao<T, ID> {

    Optional<T> findById(ID id);

    T getRecordById(ID id) throws RuntimeException;

    T saveRecord(T record);
}
