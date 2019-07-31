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

  public Review findReviewById(Integer rid){
    Review review = reviewRepository.findById(rid).get();
    return review;
  }
    public List<Review> findAllReviews(){
    return (List<Review>)reviewRepository.findAll();
  }
}
