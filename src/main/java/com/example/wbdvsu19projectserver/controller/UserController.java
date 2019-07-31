package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.sevices.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */

@RestController
public class UserController {


  @Autowired
  UserService userService;

  @PostMapping("/api/user")
  public List<User> createUser(@RequestBody User newUser) {
    return userService.createUser(newUser);
  }


  @GetMapping("api/user")
  public List<User> findAllUsers() {
    return userService.findAllUsers();
  }

  @GetMapping("/api/user/{uid}")
  public User findUserById(@PathVariable("uid") Integer uid) {
    return userService.findUserById(uid);
  }

  @PostMapping("/api/user/{uid}/product/{pid}")
  public Set<Product> addProductToUser(
          @PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid) {
    userService.addProductToUser(uid, pid);
    return getAllProductsFromUserById(uid);
  }

  @GetMapping("/api/user/{uid}/product")
  public Set<Product> getAllProductsFromUserById(@PathVariable("uid") Integer uid) {
    return userService.getAllProductsFromUserById(uid);
  }

  @DeleteMapping("/api/user/{uid}/product/{pid}")
  public Set<Product> DeleteProductFromUserById(@PathVariable("uid") Integer uid, @PathVariable("pid") Integer pid) {
    userService.deleteProductFromUser(uid, pid);
    return userService.getAllProductsFromUserById(uid);
  }
}
