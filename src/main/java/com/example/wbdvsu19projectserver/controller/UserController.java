package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Portfolio;
import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.sevices.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */

@RestController
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  HttpSession session;

  @PostMapping("/api/login")
  public String login(@RequestBody User loginUser, HttpServletResponse response) {
    User user = userService.validate(loginUser.getUsername(), loginUser.getPassword());
    if (user != null) {
      session.setAttribute("currentUserId", user.getId());
      Cookie cookie = new Cookie("JSESSIONID", session.getId());
      cookie.setMaxAge(30 * 60);// set expire time to 30 mins
      cookie.setPath("/");
      response.addCookie(cookie);
      return "user found";
    } else if (user == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
    }
    return null;
  }

  @GetMapping("/api/profile/{uid}")
  public User getProfile(@PathVariable("uid") Integer uid) {
    Integer loggedInUserId = (Integer) session.getAttribute("currentUserId");
    User user = userService.findUserById(uid);
    System.out.println(session.getId());
    if (user != null){
      if (loggedInUserId != null){
        System.out.println("**************");
        return userService.getPrivateUserProfile(loggedInUserId);
      }
      return userService.getPublicUserProfile(uid);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session Not Found");
  }


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

  @GetMapping("/api/user/{username}/portfolio")
  public Portfolio getPortfolioForUserByUsername(@PathVariable("username") String username){
    return null;
  }
}
