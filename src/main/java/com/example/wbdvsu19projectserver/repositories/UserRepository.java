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
  @Query("SELECT user.username, user.firstName, user.lastName " +
          "FROM User user WHERE user.id = :uid ")
  List<Object[]> findPublicUserProfileByUserId(@Param("uid") Integer uid);

  @Query("SELECT user.username, user.firstName, user.lastName, user.password, user.email " +
          "FROM User user WHERE user.id = :uid")
  List<Object[]> findPrivateUserProfileByUserId(@Param("uid") Integer uid);

  @Query("SELECT user FROM User user WHERE user.username = :username")
  User findUserByUsername(@Param("username") String username);

}
