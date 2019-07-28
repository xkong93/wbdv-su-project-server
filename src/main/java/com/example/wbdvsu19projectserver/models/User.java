package com.example.wbdvsu19projectserver.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */
@Entity
public class User extends Person {

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

  @ManyToMany
  @JoinTable(name = "USER_PRODUCT",
          joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
          inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID"))
  private List<Product> collectedProducts;


  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public List<Product> getCollectedProducts() {
    return collectedProducts;
  }

  public void setCollectedProducts(List<Product> collectedProducts) {
    this.collectedProducts = collectedProducts;
  }
  public void addProductToUser(Product product){
    this.collectedProducts.add(product);
    if (!product.getCollectedUsers().contains(this)){
      product.getCollectedUsers().add(this);
    }
  }
}
