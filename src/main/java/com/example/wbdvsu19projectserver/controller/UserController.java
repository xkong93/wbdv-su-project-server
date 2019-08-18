package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Person;
import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.repositories.PersonRepository;
import com.example.wbdvsu19projectserver.sevices.ProductService;
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
import org.springframework.web.bind.annotation.PutMapping;
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
  ProductController productController;

  @Autowired
  UserService userService;

  @Autowired
  PersonRepository personRepository;

  @Autowired
  ProductService productService;
  @Autowired
  HttpSession session;

  @PostMapping("/api/login")
  public ObjectNode login(@RequestBody Person loginUser, HttpServletResponse response) {
    Person person = userService.validate(loginUser.getUsername(), loginUser.getPassword());
    if (person != null) {
      session.setAttribute("currentUserId", person.getId());

      Cookie cookie = new Cookie("JSESSIONID", session.getId());
      cookie.setMaxAge(30 * 60);// set expire time to 30 mins
      cookie.setPath("/");
      cookie.setSecure(true);
//      cookie.setHttpOnly(true);
      cookie.setDomain("calm-taiga-99221.herokuapp.com");
      response.addCookie(cookie);
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode root = mapper.createObjectNode();
      root.put("dtype", person.getDtype());
      root.put("uid", person.getId());
      System.out.println(root);
      return root;
    } else if (person == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");
    }
    return null;
  }

  @GetMapping("/api/user/{uid}/publicProfile")
  public Person getPublicProfile(@PathVariable("uid") Integer uid) {
    try {
      Person person = personRepository.findById(uid).get();
      if (person != null) {
        return userService.getPublicUserProfile(uid);
      }
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
    }
    return null;
  }


  @GetMapping("/api/user/{uid}/privateProfile")
  public Person getPrivateProfile(@PathVariable("uid") Integer uid) {
    Integer loggedInUserId;
    try {
      loggedInUserId = (Integer) session.getAttribute("currentUserId");
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session Not Found");
    }

    if (loggedInUserId.equals(uid)) {
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

  @GetMapping("/api/user/{uid}/product/{urlKey}")
  public Set<Product> addProductToUser(
          @PathVariable("uid") Integer uid, @PathVariable("urlKey") String urlKey) {

    User u = userService.findUserById(uid);
    Product p = productService.findProductByUrlkey(urlKey);
    if (u.getCollectedProducts().contains(p)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "product already exists");
    }
    userService.addProductToUser(uid, urlKey);
    return getAllProductsFromUserById(uid);
  }

  //BUGGY  need to fix !!
//    @PostMapping("/api/user/{uid}/product/{urlKey}")
//  public Set<Product> addProductToUser(
//          @PathVariable("uid") Integer uid, @PathVariable("urlKey") String urlKey) {
//    Product p = productService.findProductByUrlkey(urlKey);
//
//    if (p == null){
//      productController.createProduct(urlKey);
//    }
//    System.out.println("controller" +urlKey);
//    userService.addProductToUser2(uid, urlKey);
//
//    return getAllProductsFromUserById(uid);
//  }
  @PutMapping("/api/user/{uid}")
  public Person updateUserById(
          @RequestBody User newUser,
          @PathVariable("uid") Integer uid) {
    return userService.updateUserById(uid, newUser);
  }

  @PostMapping("/api/username/{username}")
  public User findUserByUsername(@PathVariable("username") String u){
    return userService.findUserByUsername(u);
  }
  
  @GetMapping("/api/user/{uid}/product")
  public Set<Product> getAllProductsFromUserById(@PathVariable("uid") Integer uid) {
    return userService.getAllProductsFromUserById(uid);
  }

  @DeleteMapping("/api/user/{uid}/product/{urlKey}")
  public void DeleteProductFromUserById(@PathVariable("uid") Integer uid, @PathVariable("urlKey") String urlKey) {
    userService.deleteProductFromUser(uid, urlKey);
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
