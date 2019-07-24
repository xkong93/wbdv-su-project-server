package com.example.wbdvsu19projectserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */
@Entity
@Table(name = "product")
public class Product {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String sku;
  private String description;
  private int marketPrice;
  private String imageUrl;

  @OneToMany(mappedBy = "product")
  private List<Review> reviews;

  @ManyToMany(mappedBy = "collectedProducts")
  @JsonIgnore
  private List<User> collectedUsers;


  @ManyToOne
  @JsonIgnore
  private Editor editor;


  public List<Review> getReviews() {
    return reviews;
  }

  public Product(String sku, String description, int marketPrice, String imageUrl) {
    this.sku = sku;
    this.description = description;
    this.marketPrice = marketPrice;
    this.imageUrl = imageUrl;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public List<User> getCollectedUsers() {
    return collectedUsers;
  }

  public void setCollectedUsers(List<User> collectedUsers) {
    this.collectedUsers = collectedUsers;
  }

  public Editor getEditor() {
    return editor;
  }

  public void setEditor(Editor editor) {
    this.editor = editor;
  }
}
