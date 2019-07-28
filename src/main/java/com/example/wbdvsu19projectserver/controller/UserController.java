package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.sevices.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */

@RestController
public class UserController {



  @Autowired
  UserService service;

  @PostMapping("/api/user")
  public List<User> createUser(@RequestBody User newUser) {
    return service.createUser(newUser);
  }


  @GetMapping("api/user")
  public List<User> findAllUsers() {
    return service.findAllUsers();
  }

  @GetMapping("/api/user/{uid}")
  public User findUserById(@PathVariable("uid") Long uid) {
    return service.findUserById(uid);
  }

  @PostMapping("/api/user/{uid}/product/{pid}")
  public List<Product> addProductToUser(
          @PathVariable("uid") Long uid, @PathVariable("pid") Long pid) {
     service.addProductToUser(uid,pid);
     return getAllProductsFromUserById(uid);
  }

  @GetMapping("/api/user/{uid}/product")
  public List<Product> getAllProductsFromUserById (@PathVariable("uid") Long uid){
    return service.getAllProductsFromUserById(uid);
  }
}
