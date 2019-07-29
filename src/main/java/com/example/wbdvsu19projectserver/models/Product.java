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
  private String title;
  private String urlKey;
  private String uiud;
  private String description;
  private int marketPrice;
  private int retailPrice;
  private String imageUrl;
  private String styleCode;
  private String colorway;
  private String releaseDate;
  private String brand;


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

  public Product(String title, String urlKey, String uiud, String description, int marketPrice, int retailPrice, String imageUrl, String styleCode, String colorway, String releaseDate, String brand) {
    this.title = title;
    this.urlKey = urlKey;
    this.uiud = uiud;
    this.description = description;
    this.marketPrice = marketPrice;
    this.retailPrice = retailPrice;
    this.imageUrl = imageUrl;
    this.styleCode = styleCode;
    this.colorway = colorway;
    this.releaseDate = releaseDate;
    this.brand = brand;
  }

  public Product(){
    super();
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUrlKey() {
    return urlKey;
  }

  public void setUrlKey(String urlKey) {
    this.urlKey = urlKey;
  }

  public String getUiud() {
    return uiud;
  }

  public void setUiud(String uiud) {
    this.uiud = uiud;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(int marketPrice) {
    this.marketPrice = marketPrice;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void addUserToProduct(User user){
    this.collectedUsers.add(user);
    if(!user.getCollectedProducts().contains(this)){
      user.getCollectedProducts().add(this);
    }
  }

  public int getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(int retailPrice) {
    this.retailPrice = retailPrice;
  }

  public String getStyleCode() {
    return styleCode;
  }

  public void setStyleCode(String styleCode) {
    this.styleCode = styleCode;
  }

  public String getColorway() {
    return colorway;
  }

  public void setColorway(String colorway) {
    this.colorway = colorway;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
