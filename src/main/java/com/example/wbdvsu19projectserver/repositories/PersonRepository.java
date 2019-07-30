package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.Person;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
public interface PersonRepository extends CrudRepository<Person,Integer> {
}
