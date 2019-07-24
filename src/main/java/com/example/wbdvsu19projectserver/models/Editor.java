package com.example.wbdvsu19projectserver.models;

import java.util.List;

import javax.persistence.Entity;

/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */

@Entity
public class Editor extends Person {


  private List<Product> featuredList;

  public List<Product> getFeaturedList() {
    return featuredList;
  }

  public void setFeaturedList(List<Product> featuredList) {
    this.featuredList = featuredList;
  }

}