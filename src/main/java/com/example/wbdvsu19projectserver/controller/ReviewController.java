package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Rating;
import com.example.wbdvsu19projectserver.models.Review;
import com.example.wbdvsu19projectserver.sevices.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-31.
 */
@RestController
@CrossOrigin("*")
public class ReviewController {
  @Autowired
  ReviewService reviewService;


  @PostMapping("/api/user/{uid}/product/{pid}/review")
  public Review createReviewForProductByUser(@PathVariable("pid") Integer pid,
                                            @PathVariable("uid") Integer uid,
                                            @RequestBody Review review) {

    return reviewService.createReviewForProuctByUser(review, pid, uid);

  }

  @GetMapping("/api/review")
  public List<Review> getAllReviews() {
    return reviewService.findAllReviews();
  }

  @GetMapping("/api/user/{uid}/product/{pid}/review")
  public Review getReviewByUserForProduct(@PathVariable("pid") Integer pid,
                                          @PathVariable("uid") Integer uid) {
    return reviewService.getReviewByUserForProduct(uid, pid);
  }

  @GetMapping("/api/user/{uid}/review")
  public List<Review> getAllReviewsByUser(@PathVariable("uid") Integer uid) {
    return reviewService.getAllReviewsByUser(uid);
  }

  //how to resolve ambiguous api endpoint
//  @GetMapping("/api/product/{pid}/review")
//  public List<Review> getAllReviewsForProductById(@PathVariable("pid") Integer pid) {
//    return reviewService.getAllReviewsForProductById(pid);
//  }
  @GetMapping("/api/product/{urlKey}/review")
  public List<Review> getAllReviewsForProductByUrlkey(@PathVariable("urlKey") String urlKey) {
    try {
      return reviewService.getAllReviewsForProductByUrlKey(urlKey);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RATING NOT FOUND", e);
    }
  }

  @GetMapping("/api/product/{urlKey}/rating")
  public Rating getRatingForProductByUrlkey(@PathVariable("urlKey") String urlKey) {
    try {
      return reviewService.getRatingForProducyById(urlKey);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND", e);
    }
  }

  @PutMapping("api/review/{rid}")
  public Review updateReview(@PathVariable("rid") Integer rid, @RequestBody Review newReview) {
    return reviewService.updateReview(rid, newReview);
  }

  @DeleteMapping("api/reivew/{rid}")
  public void deleteReview(@PathVariable("rid") Integer rid) {
    reviewService.deleteReview(rid);
  }
}
