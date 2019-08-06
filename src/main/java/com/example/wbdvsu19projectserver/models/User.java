package com.example.wbdvsu19projectserver.models;

import java.util.List;
import java.util.Set;

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
  private Set<Product> collectedProducts;

public User() {
	super();
}

public User(String username, String email, String password, String firstName, String lastName) {
	super(username, email, password, firstName, lastName);
}

public User(String username, String email, String password, String firstName, String lastName,
		Set<Product> collectedProducts) {
	super(username, email, password, firstName, lastName);
	this.collectedProducts = collectedProducts;
}

public User(String username, String email, String password, String firstName, String lastName, List<Review> reviews) {
	super(username, email, password, firstName, lastName);
	this.reviews = reviews;
}

public User(String username, String email, String password, String firstName, String lastName, List<Review> reviews,
		Set<Product> collectedProducts) {
	super(username, email, password, firstName, lastName);
	this.reviews = reviews;
	this.collectedProducts = collectedProducts;
}

public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public Set<Product> getCollectedProducts() {
    return collectedProducts;
  }

  public void setCollectedProducts(Set<Product> collectedProducts) {
    this.collectedProducts = collectedProducts;
  }
  public void addProductToUser(Product product){
    this.collectedProducts.add(product);
    if (!product.getCollectedUsers().contains(this)){
      product.getCollectedUsers().add(this);
    }
  }
  public void deleteProductFromUser(Product product){
    this.collectedProducts.remove(product);
    if (!product.getCollectedUsers().contains(this)){
      product.getCollectedUsers().remove(this);
    }
  }
  public boolean getProductFromUser(Product product){
    if (!this.getCollectedProducts().contains(product)){
      return true;
    }
    return false;
  }
}
