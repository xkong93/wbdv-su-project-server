package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Review;
import com.example.wbdvsu19projectserver.sevices.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-31.
 */
@RestController
public class ReviewController {
  @Autowired
  ReviewService reviewService;


  @PostMapping("/api/user/{uid}/product/{pid}/review")
  public Review createReviewForProuctByUser(@PathVariable("pid") Integer pid,
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

  @GetMapping("/api/product/{pid}/review")
  public List<Review> getAllReviewsForProduct(@PathVariable("pid") Integer pid) {
    return reviewService.getAllReviewsForProduct(pid);
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
