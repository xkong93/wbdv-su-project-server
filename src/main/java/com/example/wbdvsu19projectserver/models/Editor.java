package com.example.wbdvsu19projectserver.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */

@Entity
public class Editor extends Person {

  /**
   * featureList will display customized products on the homepage by editors
   */

  @OneToMany(mappedBy = "editor")
  private List<Product> featuredProducts;

  public List<Product> getFeaturedProducts() {
    return featuredProducts;
  }

  public void setFeaturedProducts(List<Product> featuredProducts) {
    this.featuredProducts = featuredProducts;
  }
}