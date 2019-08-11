package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.Rating;
import com.example.wbdvsu19projectserver.models.Review;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.repositories.ProductRepository;
import com.example.wbdvsu19projectserver.repositories.ReviewRepository;
import com.example.wbdvsu19projectserver.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xuan Kong
 * @Date 2019-07-31.
 */
@Service
public class ReviewService {
  @Autowired
  ReviewRepository reviewRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProductRepository productRepository;

  public Review createReviewForProductByUser(Review review, Integer pid, Integer uid) {
    User user = userRepository.findById(uid).get();
    Product product = productRepository.findById(pid).get();
    review.setUser(user);
    review.setProduct(product);
    return reviewRepository.save(review);
  }

  public Review findReviewById(Integer rid) {
    Review review = reviewRepository.findById(rid).get();
    return review;
  }

  public List<Review> findAllReviews() {
    return (List<Review>) reviewRepository.findAll();
  }

  public Review getReviewByUserForProduct(Integer uid, Integer pid) {
    Review review = reviewRepository.getReviewsByUserForProduct(uid, pid);
    return review;
  }

  public List<Review> getAllReviewsByUser(Integer uid) {
    User user = userRepository.findById(uid).get();
    List<Review> reviews = user.getReviews();
    return reviews;
  }


  public List<Review> getAllReviewsForProductById(Integer pid) {
    Product product = productRepository.findById(pid).get();

    return product.getReviews();
  }

  public Rating getRatingForProducyById(String urlKey) {
    Product product = productRepository.findProductByUrlKey(urlKey);
    List<Review> reviews = product.getReviews();
    Rating rating = new Rating();
    double curWidthSum = 0;
    double curComfortSum = 0;
    double curSizeSum = 0;
    double curQualitySum = 0;
    double curOverallSum = 0;
    Integer reviewCount = 0;
    reviewCount = reviews.size();
    Integer curIsRecommendSum = 0;
    Map<Integer, Integer> ratingDistribution = new HashMap<Integer, Integer>(){{
      put(1,0);
      put(2,0);
      put(3,0);
      put(4,0);
      put(5,0);
    }};
    for (Review review : reviews) {
      curWidthSum += review.getWidth();
      System.out.println(review.getWidth());
      curComfortSum += review.getComfort();
      curSizeSum += review.getSize();
      curQualitySum += review.getQuality();
      curOverallSum += review.getOverall();
      curIsRecommendSum += review.getIsRecommend() ? 1 : 0;

      if (review.getOverall() == 1) {
        ratingDistribution.put(1, ratingDistribution.getOrDefault(1, 0) + 1);
      } else if (review.getOverall() == 2) {
        ratingDistribution.put(2, ratingDistribution.getOrDefault(2, 0) + 1);
      } else if (review.getOverall() == 3) {
        ratingDistribution.put(3, ratingDistribution.getOrDefault(3, 0) + 1);
      } else if (review.getOverall() == 4) {
        ratingDistribution.put(4, ratingDistribution.getOrDefault(4, 0) + 1);
      } else {
        ratingDistribution.put(5, ratingDistribution.getOrDefault(5, 0) + 1);
      }
    }
    DecimalFormat df=new DecimalFormat(".##");
    rating.setWidth(Double.valueOf(df.format(curWidthSum / reviewCount)));
    rating.setComfort(Double.valueOf(df.format(curComfortSum / reviewCount)));
    rating.setSize(Double.valueOf(df.format(curSizeSum / reviewCount)));
    rating.setQuality(Double.valueOf(df.format(curQualitySum / reviewCount)));
    rating.setOverall(Double.valueOf(df.format(curOverallSum / reviewCount)));
    rating.setPercentageIsRecommend(curIsRecommendSum / reviewCount);
    rating.setReviewCount(reviewCount);
    rating.setRatingDistribution(ratingDistribution);
    System.out.println(reviewCount);
    System.out.println(rating.getWidth());
    return rating;
  }

  public List<Review> getAllReviewsForProductByUrlKey(String urlKey) {
    Product product = productRepository.findProductByUrlKey(urlKey);
    List<Review> reviews = product.getReviews();
    return reviews;
  }

  public Review updateReview(Integer rid, Review newReview) {
    Review review = reviewRepository.findById(rid).get();
    review.setComfort(newReview.getComfort());
    review.setDescription(newReview.getDescription());
    review.setOverall(newReview.getOverall());
    review.setRecommend(newReview.getIsRecommend());
    review.setSize(newReview.getSize());
    review.setWidth(newReview.getWidth());
    review.setQuality(newReview.getQuality());
    return reviewRepository.save(review);
  }

  public void deleteReview(Integer rid) {
    reviewRepository.deleteById(rid);
  }
}
