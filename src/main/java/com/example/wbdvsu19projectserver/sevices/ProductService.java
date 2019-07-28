package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.repositories.ProductRepository;
import com.example.wbdvsu19projectserver.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-24.
 */
@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;


  public Product createProduct(Product newProduct){
    return productRepository.save(newProduct);
  }

  public void addUserToProduct(Long uid, Long pid) {
    User user = userRepository.findById(uid).get();
    Product product = productRepository.findById(pid).get();
    product.addUserToProduct(user);
    productRepository.save(product);
  }

  public List<Product> findAllProducts(){
    return (List<Product>)productRepository.findAll();
  }

  public Product findProductById(Long pid){
    return productRepository.findById(pid).get();
  }



}
