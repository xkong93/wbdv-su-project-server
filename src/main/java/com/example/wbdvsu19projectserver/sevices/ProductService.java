package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.repositories.ProductRepository;
import com.example.wbdvsu19projectserver.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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


  public List<Product> createProduct(Product newProduct){
       productRepository.save(newProduct);
       return findAllProducts();
  }

  public void addUserToProduct(Integer uid, Integer pid) {
    User user = userRepository.findById(uid).get();
    Product product = productRepository.findById(pid).get();
    product.addUserToProduct(user);
  }

  public List<Product> findAllProducts(){
    return (List<Product>)productRepository.findAll();
  }

  public Product findProductById(Integer pid){
    return productRepository.findById(pid).get();
  }

  public List<User> getAllUsersFromProductById(Integer pid){
    Product product = productRepository.findById(pid).get();
    return product.getCollectedUsers();
  }
  public Product findProductByUrlkey(String urlKey){
    return productRepository.findProductByUrlKey(urlKey);
  }

  public void deleteProduct(Integer pid){
    productRepository.deleteById(pid);
  }
}
