package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  //如果加了user.reviews 和 user.collections 会报错
  @Query("SELECT person.username, person.firstName, person.lastName " +
          "FROM Person person WHERE person.id = :uid ")
  List<Object[]> findPublicPersonrProfileByUserId(@Param("uid") Integer uid);

  @Query("SELECT perosn.username, perosn.firstName, perosn.lastName, perosn.password, perosn.email " +
          "FROM Person perosn WHERE perosn.id = :uid")
  List<Object[]> findPrivatePersonProfileByUserId(@Param("uid") Integer uid);

  @Query("SELECT person FROM Person person WHERE person.username = :username")
  User findUserByUsername(@Param("username") String username);

}
