package com.example.wbdvsu19projectserver.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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

  @OneToMany(mappedBy = "editor",
		  cascade=CascadeType.ALL)
  private Set<Product> featuredProducts;

  public Set<Product> getFeaturedProducts() {
    return featuredProducts;
  }

  public void setFeaturedProducts(Set<Product> featuredProducts) {
    this.featuredProducts = featuredProducts;
  }
}