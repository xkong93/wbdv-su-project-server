package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.Person;
import com.example.wbdvsu19projectserver.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
  @Query("SELECT person FROM Person person WHERE person.username = :username and person.password = :password")
  User findUserByCredentials
          (@Param("username") String username,
           @Param("password") String password);
}

