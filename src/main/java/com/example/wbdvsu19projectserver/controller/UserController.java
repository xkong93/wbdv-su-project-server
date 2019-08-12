package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.sevices.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

  @GetMapping("/api/user/{uid}/publicProfile")
  public User getPublicProfile(@PathVariable("uid") Integer uid) {
    try {
      User user = userService.findUserById(uid);
      if (user != null) {
        return userService.getPublicUserProfile(uid);
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
    }
    return null;
  }


  @GetMapping("/api/user/{uid}/privateProfile")
  public User getPrivateProfile(@PathVariable("uid") Integer uid) {
    Integer loggedInUserId;
    try {
      loggedInUserId = (Integer) session.getAttribute("currentUserId");
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session Not Found");
    }

    if (loggedInUserId == uid) {
      return userService.getPrivateUserProfile(loggedInUserId);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Access");
    }
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

  @PostMapping("/api/user/{uid}")
  public User updateUserById(
          @RequestBody User newUser,
          @PathVariable("uid") Integer uid) {
    return userService.updateUserById(uid, newUser);
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

  @GetMapping("/api/user/{uid}/portfolio")
  public ObjectNode getPortfolioForUserByUserId(@PathVariable("uid") Integer uid) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode root = mapper.createObjectNode();
    User user = userService.findUserById(uid);
    Set<Product> collection = user.getCollectedProducts();
    ArrayNode portfolioItems = root.putArray("portfolioItems");
    Integer retailSum = 0;
    Integer marketSum = 0;
    Integer gainLossSum = 0;
    for (Product p : collection) {
      ObjectNode item = mapper.createObjectNode();
      item.put("productId", p.getId());
      item.put("productUrlKey", p.getUrlKey());
      item.put("productUuid", p.getUuid());
      item.put("brand", p.getBrand());
      item.put("name", p.getTitle());
      item.put("imageUrl", p.getImageUrl());
      item.put("marketPrice", p.getMarketPrice());
      item.put("retailPrice", p.getRetailPrice());
      item.put("releaseDate", p.getReleaseDate());
      Integer gainLoss = p.getMarketPrice() - p.getRetailPrice();
      item.put("gainLoss", gainLoss);
      retailSum += p.getRetailPrice();
      marketSum += p.getMarketPrice();
      gainLossSum += gainLoss;
      portfolioItems.add(item);
    }
    root.put("totalItem", collection.size());
    root.put("retailSum", retailSum);
    root.put("marketSum", marketSum);
    root.put("gainLossSum", gainLossSum);
    return root;
  }
}
