package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.Review;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.repositories.PersonRepository;
import com.example.wbdvsu19projectserver.repositories.ProductRepository;
import com.example.wbdvsu19projectserver.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PersonRepository personRepository;

  @Autowired
  ProductRepository productRepository;

  public List<User> createUser(User newUser) {
    userRepository.save(newUser);
    return findAllUsers();
  }

  public void addProductToUser(Integer uid, String urlKey) {

    User user = userRepository.findById(uid).get();
    Product product = productRepository.findProductByUrlKey(urlKey);
    user.addProductToUser(product);
    userRepository.save(user);
  }
//  public void addProductToUser(Integer uid, Integer pid) {
//    User user = userRepository.findById(uid).get();
//    Product product = productRepository.findById(pid).get();
//    user.addProductToUser(product);
//    userRepository.save(user);
//  }

  public List<User> findAllUsers() {
    return (List<User>) userRepository.findAll();
  }

  public User findUserById(Integer uid) {
    User user = userRepository.findById(uid).get();
    return user;
  }

  public User findUserByUsername(String username) {
    User user = userRepository.findUserByUsername(username);
    return user;
  }

  public Set<Product> getAllProductsFromUserById(Integer uid) {
    User user = userRepository.findById(uid).get();
    return user.getCollectedProducts();
  }

  public Set<Product> deleteProductFromUser(Integer uid, Integer pid) {
    User user = userRepository.findById(uid).get();
    Product product = productRepository.findById(pid).get();
    user.deleteProductFromUser(product);
    userRepository.save(user);
    return getAllProductsFromUserById(uid);
  }

  public User validate(String username, String password) {
    User user = personRepository.findUserByCredentials(username, password);
    return user;
  }

  public User getPublicUserProfile(Integer uid) {
    List<Object[]> list = userRepository.findPrivateUserProfileByUserId(uid);
    User user = new User();
    for (Object[] object : list) {
      user.setUsername((String) object[0]);
      user.setFirstName((String) object[1]);
      user.setLastName((String) object[2]);
      //如果加了review 和collectedProducts 会报错 type 转型？？
    }
    return user;
  }

  public User getPrivateUserProfile(Integer uid) {
    List<Object[]> list = userRepository.findPrivateUserProfileByUserId(uid);
    User user = new User();
    for (Object[] object : list) {
      user.setUsername((String) object[0]);
      user.setFirstName((String) object[1]);
      user.setLastName((String) object[2]);
      user.setPassword((String) object[3]);
      user.setEmail((String) object[4]);
    }
    return user;
  }

  public User updateUserById(Integer uid,User newUser){
    User user = userRepository.findById(uid).get();
    user.setPassword(newUser.getPassword());
    user.setLastName(newUser.getLastName());
    user.setFirstName(newUser.getFirstName());
    return userRepository.save(user);
  }
}
