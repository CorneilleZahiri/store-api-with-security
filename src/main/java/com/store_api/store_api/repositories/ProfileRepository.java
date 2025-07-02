package com.store_api.store_api.repositories;


import com.store_api.store_api.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}