package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.Person;
import com.example.wbdvsu19projectserver.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
public interface PersonRepository extends CrudRepository<Person,Integer> {
    @Query("SELECT person FROM Person person where person.username = :username and person.password = :password")
	List<User> findUserByCredentials
	(@Param("username") String username,
	        @Param("password") String password);
}
