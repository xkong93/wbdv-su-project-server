package com.example.wbdvsu19projectserver.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xuan Kong
 * @Date 2019-08-02.
 */
public class Rating {

  private double averageWidth;

  private double averageComfort;

  private double averageSize;


  private double averageQuality;

  private double averageOverall;

  private Integer reviewCount;

  private double percentageIsRecommend;

  private Map<Integer, Integer> ratingDistribution;


  public Rating (){

  }
  public double getAverageWidth() {
    return averageWidth;
  }

  public void setAverageWidth(double averageWidth) {
    this.averageWidth = averageWidth;
  }

  public double getAverageComfort() {
    return averageComfort;
  }

  public void setAverageComfort(double averageComfort) {
    this.averageComfort = averageComfort;
  }

  public double getAverageSize() {
    return averageSize;
  }

  public void setAverageSize(double averageSize) {
    this.averageSize = averageSize;
  }

  public double getAverageQuality() {
    return averageQuality;
  }

  public void setAverageQuality(double averageQuality) {
    this.averageQuality = averageQuality;
  }

  public double getAverageOverall() {
    return averageOverall;
  }

  public void setAverageOverall(double averageOverall) {
    this.averageOverall = averageOverall;
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
