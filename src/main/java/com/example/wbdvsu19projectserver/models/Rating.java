package com.example.wbdvsu19projectserver.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuan Kong
 * @Date 2019-08-02.
 */
public class Rating {

  private double Width;

  private double Comfort;

  private double Size;


  private double Quality;

  private double Overall;

  private Integer reviewCount;

  private double percentageIsRecommend;

  private Map<Integer, Integer> ratingDistribution;


  public Rating (){

  }
  public double getWidth() {
    return Width;
  }

  public void setWidth(double Width) {
    this.Width = Width;
  }

  public double getComfort() {
    return Comfort;
  }

  public void setComfort(double Comfort) {
    this.Comfort = Comfort;
  }

  public double getSize() {
    return Size;
  }

  public void setSize(double Size) {
    this.Size = Size;
  }

  public double getQuality() {
    return Quality;
  }

  public void setQuality(double Quality) {
    this.Quality = Quality;
  }

  public double getOverall() {
    return Overall;
  }

  public void setOverall(double Overall) {
    this.Overall = Overall;
  }

  public Integer getReviewCount() {
    return reviewCount;
  }

  public void setReviewCount(Integer reviewCount) {
    this.reviewCount = reviewCount;
  }

  public double getPercentageIsRecommend() {
    return percentageIsRecommend;
  }

  public void setPercentageIsRecommend(Integer percentageIsRecommend) {
    this.percentageIsRecommend = percentageIsRecommend;
  }

  public Map<Integer, Integer> getRatingDistribution() {
    return ratingDistribution;
  }

  public void setRatingDistribution(Map<Integer, Integer> ratingDistribution) {
    this.ratingDistribution = ratingDistribution;
  }
}
