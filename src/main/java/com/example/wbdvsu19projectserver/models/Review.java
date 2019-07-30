package com.example.wbdvsu19projectserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */
@Entity
@Table(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private int size;
  private int width;
  private int comfort;
  private int quality;
  private int overall;
  private boolean isRecommend;
  private String description;
  private String imageUrl;

  @ManyToOne
  @JsonIgnore
  private User user;

  @ManyToOne
  @JsonIgnore
  private Product product;

  public Review(int size, int width, int comfort, int quality, int overall, boolean isRecommend, String description, String imageUrl) {
    this.size = size;
    this.width = width;
    this.comfort = comfort;
    this.quality = quality;
    this.overall = overall;
    this.isRecommend = isRecommend;
    this.description = description;
    this.imageUrl = imageUrl;
  }
  public Review(){
  }
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getComfort() {
    return comfort;
  }

  public void setComfort(int comfort) {
    this.comfort = comfort;
  }

  public int getQuality() {
    return quality;
  }

  public void setQuality(int quality) {
    this.quality = quality;
  }

  public int getOverall() {
    return overall;
  }

  public void setOverall(int overall) {
    this.overall = overall;
  }

  public boolean isRecommend() {
    return isRecommend;
  }

  public void setRecommend(boolean recommend) {
    isRecommend = recommend;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }


}
