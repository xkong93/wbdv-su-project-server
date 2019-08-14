package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Person;
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

  public void deleteProductFromUser(Integer uid, String urlKey) {
    User user = userRepository.findById(uid).get();
    Product product = productRepository.findProductByUrlKey(urlKey);
    user.deleteProductFromUser(product);
    productRepository.save(product);
    userRepository.save(user);
  }

  public Person validate(String username, String password) {
    Person person = personRepository.findUserByCredentials(username, password);
    return person;
  }

  public Person getPublicUserProfile(Integer uid) {
    List<Object[]> list = userRepository.findPrivatePersonProfileByUserId(uid);
    Person person = new Person();
    for (Object[] object : list) {
      person.setUsername((String) object[0]);
      person.setFirstName((String) object[1]);
      person.setLastName((String) object[2]);
      //如果加了review 和collectedProducts 会报错 type 转型？？
    }
    return person;
  }

  public Person getPrivateUserProfile(Integer uid) {
    List<Object[]> list = userRepository.findPrivatePersonProfileByUserId(uid);
    Person person = new Person();
    for (Object[] object : list) {
      person.setUsername((String) object[0]);
      person.setFirstName((String) object[1]);
      person.setLastName((String) object[2]);
      person.setPassword((String) object[3]);
      person.setEmail((String) object[4]);
    }
    return person;
  }

  public Person updateUserById(Integer uid,Person newUser){
    Person person = personRepository.findById(uid).get();
    person.setPassword(newUser.getPassword());
    person.setLastName(newUser.getLastName());
    person.setFirstName(newUser.getFirstName());
    return personRepository.save(person);
  }


}
