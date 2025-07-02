package com.store_api.store_api.repositories;

import com.store_api.store_api.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}