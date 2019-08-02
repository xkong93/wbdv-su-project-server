package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.Review;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.repositories.ProductRepository;
import com.example.wbdvsu19projectserver.repositories.ReviewRepository;
import com.example.wbdvsu19projectserver.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  public Review createReviewForProuctByUser(Review review, Integer pid, Integer uid) {
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
    List<Review> reviews = product.getReviews();
    return reviews;
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

  public void deleteReview(Integer rid){
    reviewRepository.deleteById(rid);
  }
}
